package com.example.Uber_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRatingResponseDTO {
    private Long driverId;
    private double averageRating; // 0.0 if no reviews
    private long totalReviews;
}
