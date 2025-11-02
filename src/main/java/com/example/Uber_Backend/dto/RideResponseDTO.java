package com.example.Uber_Backend.dto;

import com.example.Uber_Backend.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideResponseDTO {
    private Long id;
    private RideStatus status;
    private String pickupLocation;
    private String dropOffLocation;
    private LocalDateTime requestedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private PassengerInfo passenger;
    private DriverInfo driver;

    public record PassengerInfo(
            Long id,
            String name
    ) {}

    public record DriverInfo(
            Long id,
            String name
    ) {}

}


