package com.example.Uber_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private String currentLocation;
    private boolean isAvailable;
}
