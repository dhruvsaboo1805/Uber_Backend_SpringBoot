package com.example.Uber_Backend.mappers;

import com.example.Uber_Backend.dto.RideResponseDTO;
import com.example.Uber_Backend.entities.Driver;
import com.example.Uber_Backend.entities.RideBooking;

public class RideMapper {
    public static RideResponseDTO toDto(RideBooking rideBooking) {

        RideResponseDTO.PassengerInfo passengerInfo = new RideResponseDTO.PassengerInfo(
                rideBooking.getPassenger().getId(),
                rideBooking.getPassenger().getName()
        );

        RideResponseDTO.DriverInfo driverInfo = null;
        if (rideBooking.getDriver() != null) {
            Driver driver = rideBooking.getDriver();
            driverInfo = new RideResponseDTO.DriverInfo(
                    driver.getId(),
                    driver.getName()
            );
        }

        RideResponseDTO rideResponseDTO = new RideResponseDTO().builder()
                .id(rideBooking.getId())
                .status(rideBooking.getStatus())
                .pickupLocationLatitude(rideBooking.getPickupLocationLatitude())
                .pickupLocationLongitude(rideBooking.getPickupLocationLongitude())
                .requestedAt(rideBooking.getRequestedAt())
                .startedAt(rideBooking.getCompletedAt())
                .completedAt(rideBooking.getCompletedAt())
                .driverId(rideBooking.getDriver().getId())
                .passengerId(rideBooking.getPassenger().getId())
                .build();

        return rideResponseDTO;
    }
}
