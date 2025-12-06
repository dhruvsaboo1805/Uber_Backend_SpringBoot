package com.example.Uber_Backend.controllers;

import com.example.Uber_Backend.dto.RideRequestDTO;
import com.example.Uber_Backend.dto.RideResponseDTO;
import com.example.Uber_Backend.services.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    @PostMapping
    public ResponseEntity<RideResponseDTO> createRide(@RequestBody RideRequestDTO requestDto) throws Exception {
        RideResponseDTO ride = rideService.createRide(requestDto);
        return new ResponseEntity<>(ride, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideResponseDTO> getRideById(@PathVariable Long id) throws Exception {
        RideResponseDTO ride = rideService.getRideById(id);
        return ResponseEntity.ok(ride);
    }

    @GetMapping
    public ResponseEntity<List<RideResponseDTO>> getAllRides() {
        List<RideResponseDTO> rides = rideService.getAllRides();
        return ResponseEntity.ok(rides);
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<RideResponseDTO> acceptRide(@PathVariable Long id, Long driverId) throws Exception {
        RideResponseDTO updatedRide = rideService.confirmRideAcceptance(id, driverId);
        return ResponseEntity.ok(updatedRide);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) throws Exception {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }
}
