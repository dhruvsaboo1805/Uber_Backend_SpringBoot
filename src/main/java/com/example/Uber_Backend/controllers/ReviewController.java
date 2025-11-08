package com.example.Uber_Backend.controllers;

import com.example.Uber_Backend.dto.DriverRatingResponseDTO;
import com.example.Uber_Backend.dto.ReviewRequestDTO;
import com.example.Uber_Backend.dto.ReviewResponseDTO;
import com.example.Uber_Backend.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<ReviewResponseDTO> create(@RequestBody ReviewRequestDTO request) {
        ReviewResponseDTO res = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/drivers/{driverId}/rating")
    public DriverRatingResponseDTO driverRating(@PathVariable Long driverId) {
        return reviewService.getDriverRating(driverId);
    }

    @PatchMapping("/update/{bookingId}")
    public ReviewResponseDTO update(@PathVariable Long bookingId,
                                 @RequestParam int rating,
                                 @RequestParam(required = false) String comment,
                                 @RequestParam Long passengerId) {
        return reviewService.updateReview(bookingId, rating, comment, passengerId);
    }

    @DeleteMapping("/delete/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long bookingId,
                       @RequestParam Long passengerId) {
        reviewService.deleteReview(bookingId, passengerId);
    }
}
