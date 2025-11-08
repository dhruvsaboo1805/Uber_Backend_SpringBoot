package com.example.Uber_Backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestDTO {
    @NotNull
    private Long driverId;

    @NotNull
    private Long passengerId;

    @NotNull
    private Long bookingId;

    @Min(1) @Max(5)
    private int rating;

    @Size(max = 2000)
    private String comment;
}
