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
    private Double pickupLocationLatitude;
    private Double pickupLocationLongitude;
    private LocalDateTime requestedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Long passengerId;
    private Long driverId;

    public record PassengerInfo(
            Long id,
            String name
    ) {}

    public record DriverInfo(
            Long id,
            String name
            ) {}
    }


