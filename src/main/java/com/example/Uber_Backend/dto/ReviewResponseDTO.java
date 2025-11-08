package com.example.Uber_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long id;
    private Long driverId;
    private Long passengerId;
    private Long bookingId;
    private int rating;
    private String comment;
    private Instant createdAt;
}
