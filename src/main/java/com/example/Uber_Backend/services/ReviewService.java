package com.example.Uber_Backend.services;

import com.example.Uber_Backend.dto.DriverRatingResponseDTO;
import com.example.Uber_Backend.dto.ReviewRequestDTO;
import com.example.Uber_Backend.dto.ReviewResponseDTO;
import com.example.Uber_Backend.entities.Driver;
import com.example.Uber_Backend.entities.Passenger;
import com.example.Uber_Backend.entities.Review;
import com.example.Uber_Backend.entities.RideBooking;
import com.example.Uber_Backend.mappers.ReviewMapper;
import com.example.Uber_Backend.repositories.IDriverRepository;
import com.example.Uber_Backend.repositories.IPassengerRepository;
import com.example.Uber_Backend.repositories.IReviewRepository;
import com.example.Uber_Backend.repositories.IRideRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final IReviewRepository reviewRepository;
    private final IDriverRepository driverRepository;
    private final IPassengerRepository passengerRepository;
    private final IRideRepository rideRepository;

    public ReviewResponseDTO createReview(ReviewRequestDTO req) {
        Driver driver = driverRepository.findById(req.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        Passenger passenger = passengerRepository.findById(req.getPassengerId())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        RideBooking booking = rideRepository.findById(req.getBookingId())
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        if (reviewRepository.existsByBookingId(booking.getId())) {
            throw new IllegalStateException("Review already exists for this booking");
        }

        // - Ensure booking belongs to this driver & passenger
        if (!booking.getDriver().getId().equals(driver.getId())) {
            throw new IllegalArgumentException("Booking does not belong to the given driver");
        }
        if (!booking.getPassenger().getId().equals(passenger.getId())) {
            throw new IllegalArgumentException("Booking does not belong to the given passenger");
        }

        Review review = new Review().builder()
                .driver(driver)
                .passenger(passenger)
                .rating(req.getRating())
                .comment(req.getComment())
                .build();
        Review saved = reviewRepository.save(review);

        return ReviewMapper.toDto(saved);
    }

    public DriverRatingResponseDTO getDriverRating(Long driverId) {
        return reviewRepository.getDriverRating(driverId);
    }


    public ReviewResponseDTO updateReview(Long bookingId, int rating, String comment, Long passengerId) {
        Review review = reviewRepository.findByBookingId(bookingId);
        if (review == null)
            throw new EntityNotFoundException("Review not found for booking");
        if (!review.getPassenger().getId().equals(passengerId)) {
            throw new IllegalStateException("Only the original passenger can update the review");
        }
        review.setRating(rating);
        review.setComment(comment);
        Review saved = reviewRepository.save(review);

        return ReviewMapper.toDto(saved);

    }

    public void deleteReview(Long bookingId, Long passengerId) {
        Review review = reviewRepository.findByBookingId(bookingId);
        if (review == null)
            return;
        if (!review.getPassenger().getId().equals(passengerId)) {
            throw new IllegalStateException("Only the original passenger can delete the review");
        }
        reviewRepository.delete(review);
    }

}
