package com.example.Uber_Backend.mappers;

import com.example.Uber_Backend.dto.RideRequestDTO;
import com.example.Uber_Backend.dto.RideResponseDTO;
import com.example.Uber_Backend.entities.Driver;
import com.example.Uber_Backend.entities.Passenger;
import com.example.Uber_Backend.entities.Ride;

public class RideMapper {
    public static RideResponseDTO toDto(Ride ride) {

        RideResponseDTO.PassengerInfo passengerInfo = new RideResponseDTO.PassengerInfo(
                ride.getPassenger().getId(),
                ride.getPassenger().getName()
        );

        RideResponseDTO.DriverInfo driverInfo = null;
        if (ride.getDriver() != null) {
            Driver driver = ride.getDriver();
            driverInfo = new RideResponseDTO.DriverInfo(
                    driver.getId(),
                    driver.getName()
            );
        }

        return new RideResponseDTO(
                ride.getId(),
                ride.getStatus(),
                ride.getPickupLocation(),
                ride.getDropOffLocation(),
                ride.getRequestedAt(),
                ride.getStartedAt(),
                ride.getCompletedAt(),
                passengerInfo,
                driverInfo
        );
    }
}
