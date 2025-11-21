package com.example.Uber_Backend.repositories;

import com.example.Uber_Backend.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDriverRepository extends JpaRepository<Driver, String> {

}
