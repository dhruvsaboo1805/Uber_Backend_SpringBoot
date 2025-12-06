package com.example.Uber_Backend.mappers;

import com.example.Uber_Backend.dto.ReviewResponseDTO;
import com.example.Uber_Backend.entities.Review;

import java.time.Instant;

// todo correct types
public class ReviewMapper {
    public static ReviewResponseDTO toDto(Review review) {
        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO().builder()
                .id(review.getId())
                .driverId(review.getDriver().getId())
                .passengerId(review.getPassenger().getId())
                .bookingId(review.getBooking().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
        return reviewResponseDTO;
    }

//    public static Review toEntity(ReviewResponseDTO reviewResponseDTO) {
//        Review review = new Review().builder()
//                .
//    }
}
