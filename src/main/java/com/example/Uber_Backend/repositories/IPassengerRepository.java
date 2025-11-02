package com.example.Uber_Backend.repositories;

import com.example.Uber_Backend.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPassengerRepository extends JpaRepository<Passenger, Long> {

}
