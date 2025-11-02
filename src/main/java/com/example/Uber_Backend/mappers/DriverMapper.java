package com.example.Uber_Backend.mappers;

import com.example.Uber_Backend.dto.DriverRequestDTO;
import com.example.Uber_Backend.dto.DriverResponseDTO;
import com.example.Uber_Backend.entities.Driver;

public class DriverMapper {
    public static Driver toEntity(DriverRequestDTO dto) {
        Driver driver = new Driver().builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .mobileNumber(dto.getMobileNumber())
                .build();
        return driver;
    }

    public static DriverResponseDTO toDto(Driver driver) {
        DriverResponseDTO driverResponseDTO = new DriverResponseDTO().builder()
                .id(driver.getId())
                .name(driver.getName())
                .email(driver.getEmail())
                .mobileNumber(driver.getMobileNumber())
                .currentLocation(driver.getCurrentLocation())
                .isAvailable(driver.isAvailable())
                .build();
        return driverResponseDTO;
    }
}
