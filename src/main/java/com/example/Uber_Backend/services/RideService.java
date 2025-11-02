package com.example.Uber_Backend.services;

import com.example.Uber_Backend.dto.RideRequestDTO;
import com.example.Uber_Backend.dto.RideResponseDTO;
import com.example.Uber_Backend.entities.Driver;
import com.example.Uber_Backend.entities.Passenger;
import com.example.Uber_Backend.entities.Ride;
import com.example.Uber_Backend.enums.RideStatus;
import com.example.Uber_Backend.mappers.RideMapper;
import com.example.Uber_Backend.repositories.IDriverRepository;
import com.example.Uber_Backend.repositories.IPassengerRepository;
import com.example.Uber_Backend.repositories.IRideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

        Ride ride = new Ride();
        ride.setPassenger(passenger);
        ride.setPickupLocation(requestDto.getPickupLocation());
        ride.setDropOffLocation(requestDto.getDropOffLocation());
        ride.setStatus(RideStatus.REQUESTED);
        ride.setRequestedAt(LocalDateTime.now());

        Ride savedRide = rideRepository.save(ride);
        return RideMapper.toDto(savedRide);
    }

    public RideResponseDTO getRideById(Long id) throws Exception {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new Exception("Ride not found with id: " + id));
        return RideMapper.toDto(ride);
    }

    public List<RideResponseDTO> getAllRides() {
        return rideRepository.findAll()
                .stream()
                .map(RideMapper::toDto)
                .collect(Collectors.toList());
    }

    public RideResponseDTO acceptRide(Long rideId, Long driverId) throws Exception {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new Exception("Ride not found with id: " + rideId));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new Exception("Driver not found with id: " + driverId));

        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new IllegalStateException("Ride cannot be accepted, current status: " + ride.getStatus());
        }

        ride.setDriver(driver);
        ride.setStatus(RideStatus.BOOKED);

        Ride updatedRide = rideRepository.save(ride);
        return RideMapper.toDto(updatedRide);
    }


    public void deleteRide(Long id) throws Exception {
        if (!rideRepository.existsById(id)) {
            throw new Exception("Ride not found with id: " + id);
        }
        rideRepository.deleteById(id);
    }
}
