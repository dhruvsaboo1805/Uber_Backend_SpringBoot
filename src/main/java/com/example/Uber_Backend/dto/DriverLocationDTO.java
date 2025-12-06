package com.example.Uber_Backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverLocationDTO {
    private Long driverId;
    private Double latitude;
    private Double longitude;
}
