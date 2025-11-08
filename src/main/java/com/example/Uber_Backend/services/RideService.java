package com.example.Uber_Backend.services;

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

    public RideResponseDTO createRide(RideRequestDTO requestDto) throws  Exception {
        Passenger passenger = passengerRepository.findById(requestDto.getPassengerId())
                .orElseThrow(() -> new Exception("Passenger not found with id: " + requestDto.getPassengerId()));

        RideBooking rideBooking = new RideBooking();
        rideBooking.setPassenger(passenger);
        rideBooking.setPickupLocation(requestDto.getPickupLocation());
        rideBooking.setDropOffLocation(requestDto.getDropOffLocation());
        rideBooking.setStatus(RideStatus.REQUESTED);
        rideBooking.setRequestedAt(LocalDateTime.now());

        RideBooking savedRideBooking = rideRepository.save(rideBooking);
        return RideMapper.toDto(savedRideBooking);
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

    public RideResponseDTO acceptRide(Long rideId, Long driverId) throws Exception {
        RideBooking rideBooking = rideRepository.findById(rideId)
                .orElseThrow(() -> new Exception("Ride not found with id: " + rideId));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new Exception("Driver not found with id: " + driverId));

        if (rideBooking.getStatus() != RideStatus.REQUESTED) {
            throw new IllegalStateException("Ride cannot be accepted, current status: " + rideBooking.getStatus());
        }

        rideBooking.setDriver(driver);
        rideBooking.setStatus(RideStatus.BOOKED);

        RideBooking updatedRideBooking = rideRepository.save(rideBooking);
        return RideMapper.toDto(updatedRideBooking);
    }


    public void deleteRide(Long id) throws Exception {
        if (!rideRepository.existsById(id)) {
            throw new Exception("Ride not found with id: " + id);
        }
        rideRepository.deleteById(id);
    }
}
