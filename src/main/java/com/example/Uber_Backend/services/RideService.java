package com.example.Uber_Backend.services;

import com.example.Uber_Backend.client.GrpcClient;
import com.example.Uber_Backend.dto.DriverLocationDTO;
import com.example.Uber_Backend.dto.RideRequestDTO;
import com.example.Uber_Backend.dto.RideResponseDTO;
import com.example.Uber_Backend.entities.Driver;
import com.example.Uber_Backend.entities.Passenger;
import com.example.Uber_Backend.entities.RideBooking;
import com.example.Uber_Backend.enums.RideStatus;
import com.example.Uber_Backend.mappers.RideMapper;
import com.example.Uber_Backend.repositories.IDriverRepository;
import com.example.Uber_Backend.repositories.IPassengerRepository;
import com.example.Uber_Backend.repositories.IRideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RideService {

    private final IRideRepository rideRepository;
    private final IPassengerRepository passengerRepository;
    private final IDriverRepository driverRepository;
    private final RedisLocationService redisLocationService;
    private final GrpcClient grpcClient;

    public RideResponseDTO createRide(RideRequestDTO request) throws  Exception {
        Passenger passenger = passengerRepository.findById(request.getPassengerId())
                .orElseThrow(() -> new Exception("Passenger not found with id: " + request.getPassengerId()));

        // Handle driver assignment if provided
        Driver driver = null;


        if (request.getDriverId()!= null) {
            driver = driverRepository.findById(request.getDriverId())
                    .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: " + request.getDriverId()));

            // Check if driver is available
            if (!driver.isAvailable()) {
                throw new IllegalStateException("Driver with id " + request.getDriverId() + " is not available");
            }

            // Assign driver and mark as unavailable
            driver.setAvailable(false);
            driverRepository.save(driver);
        }

        // // Convert latitude/longitude from Double to String
        String pickupLat = request.getPickupLocationLatitude() != null
                ? request.getPickupLocationLatitude().toString()
                : null;
        String pickupLng = request.getPickupLocationLongitude() != null
                ? request.getPickupLocationLongitude().toString()
                : null;

        if (pickupLat == null || pickupLng == null) {
            throw new IllegalArgumentException("Pickup location latitude and longitude are required");
        }


        RideBooking newBooking = RideBooking.builder()
                .passenger(passenger)
                .driver(driver)
                .pickupLocationLatitude(Double.valueOf(pickupLat))
                .pickupLocationLongitude(Double.valueOf(pickupLng))
                .status(RideStatus.BOOKED)
                .build();

        RideBooking savedBooking = rideRepository.save(newBooking);

        // Find the nearby drivers and then trigger an RPC to UberSocketService to notify them

        List<DriverLocationDTO> nearbyDrivers = redisLocationService.getNearByDrivers(Double.parseDouble(pickupLat), Double.parseDouble(pickupLng), 10.0);
        List<Integer> driverIds = nearbyDrivers.stream()
                .map(DriverLocationDTO::getDriverId)
                .map(Long::intValue)
                .collect(Collectors.toList());

        grpcClient.NotifyDrivers(pickupLat, pickupLng, Integer.parseInt(savedBooking.getId().toString()), driverIds);
        return RideMapper.toDto(savedBooking);
    }

    public RideResponseDTO getRideById(Long id) throws Exception {
        RideBooking rideBooking = rideRepository.findById(id)
                .orElseThrow(() -> new Exception("Ride not found with id: " + id));
        return RideMapper.toDto(rideBooking);
    }

    public List<RideResponseDTO> getAllRides() {
        return rideRepository.findAll()
                .stream()
                .map(RideMapper::toDto)
                .collect(Collectors.toList());
    }

    public RideResponseDTO confirmRideAcceptance(Long bookingId, Long driverId) throws Exception {
        RideBooking rideBooking = rideRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking not found"));

        if (rideBooking.getStatus() != RideStatus.REQUESTED) {
            throw new Exception("Ride already assigned or cancelled");
        }

        // Assign driver
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new Exception("Driver not found"));

        rideBooking.setDriver(driver);
        rideBooking.setStatus(RideStatus.BOOKED);
        rideBooking.setCreatedAt(LocalDateTime.now());

        rideBooking = rideRepository.save(rideBooking);

        return RideResponseDTO.builder()
                .id(rideBooking.getId())
                .driverId(driver.getId())
                .status(rideBooking.getStatus())
                .build();
    }

    public void deleteRide(Long id) throws Exception {
        if (!rideRepository.existsById(id)) {
            throw new Exception("Ride not found with id: " + id);
        }
        rideRepository.deleteById(id);
    }
}
