package com.example.Uber_Backend.repositories;

import com.example.Uber_Backend.dto.DriverRatingResponseDTO;
import com.example.Uber_Backend.entities.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByBookingId(Long bookingId);

    @Query("select new com.example.Uber_Backend.dto.DriverRatingResponseDTO(:driverId, coalesce(avg(r.rating),0), count(r)) " +
            "from Review r where r.driver.id = :driverId")
    DriverRatingResponseDTO getDriverRating(@Param("driverId") Long driverId);

    Review findByBookingId(Long bookingId);
}
