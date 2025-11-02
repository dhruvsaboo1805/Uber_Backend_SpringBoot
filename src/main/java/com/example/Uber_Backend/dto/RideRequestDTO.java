package com.example.Uber_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDTO {
    private Long passengerId;
    private String pickupLocation;
    private String dropOffLocation;
}
