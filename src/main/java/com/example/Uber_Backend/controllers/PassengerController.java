package com.example.Uber_Backend.controllers;

import com.example.Uber_Backend.dto.PassengerRequestDTO;
import com.example.Uber_Backend.dto.PassengerResponseDTO;
import com.example.Uber_Backend.services.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponseDTO> createPassenger(@RequestBody PassengerRequestDTO requestDto) {
        PassengerResponseDTO passenger = passengerService.createPassenger(requestDto);
        return new ResponseEntity<>(passenger, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDTO> getPassengerById(@PathVariable Long id) throws Exception {
        PassengerResponseDTO passenger = passengerService.getPassengerById(id);
        return ResponseEntity.ok(passenger);
    }

    @GetMapping
    public ResponseEntity<List<PassengerResponseDTO>> getAllPassengers() {
        List<PassengerResponseDTO> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(passengers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@PathVariable Long id, @RequestBody PassengerRequestDTO requestDto) throws Exception {
        PassengerResponseDTO updatedPassenger = passengerService.updatePassenger(id, requestDto);
        return ResponseEntity.ok(updatedPassenger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) throws Exception {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
