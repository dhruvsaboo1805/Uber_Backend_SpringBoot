package com.example.Uber_Backend.services;

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

    public RideResponseDTO createRide(RideRequestDTO requestDto) throws  Exception {
        Passenger passenger = passengerRepository.findById(requestDto.getPassengerId())
                .orElseThrow(() -> new Exception("Passenger not found with id: " + requestDto.getPassengerId()));

        RideBooking rideBooking = new RideBooking();
        rideBooking.setPassenger(passenger);
        rideBooking.setPickupLocationLatitude(requestDto.getPickupLocationLatitude());
        rideBooking.setPickupLocationLongitude(requestDto.getPickupLocationLongitude());
        rideBooking.setStatus(RideStatus.REQUESTED);
        rideBooking.setRequestedAt(LocalDateTime.now());

        // raise a request to find the driver
        List<DriverLocationDTO> nearbyDrivers = redisLocationService.getNearByDrivers(requestDto.getPickupLocationLatitude(), requestDto.getPickupLocationLongitude() , 10.0);

        if (nearbyDrivers.isEmpty()) {
            rideBooking.setStatus(RideStatus.NOT_AVAILABLE);
            rideRepository.save(rideBooking);
            throw new Exception("No drivers available in your area");
        }

        List<Long> driverIds = nearbyDrivers.stream()
                .map(DriverLocationDTO::getDriverId)
                .toList();

        // this is part where I have confused todo

//        try {
//            grpcClient.notifyDriversForRide(
//                    rideBooking.getId().toString(),
//                    driverIds,
//                    requestDto.getPickupLocationLatitude(),
//                    requestDto.getPickupLocationLongitude()
//            );
//        } catch (Exception e) {
//            rideBooking.setStatus(RideStatus.FAILED);
//            rideRepository.save(rideBooking);
//            throw new Exception("Failed to notify drivers: " + e.getMessage());
//        }

        return RideResponseDTO.builder()
                .id(rideBooking.getId())
                .status(rideBooking.getStatus())
                .build();
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
