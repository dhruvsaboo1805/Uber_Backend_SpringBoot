package com.example.Uber_Backend.repositories;

import com.example.Uber_Backend.entities.RideBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRideRepository extends JpaRepository<RideBooking, Long> {
}
