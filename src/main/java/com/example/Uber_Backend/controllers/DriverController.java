package com.example.Uber_Backend.controllers;

import com.example.Uber_Backend.dto.DriverRequestDTO;
import com.example.Uber_Backend.dto.DriverResponseDTO;
import com.example.Uber_Backend.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(@RequestBody DriverRequestDTO requestDto) {
        DriverResponseDTO driver = driverService.createDriver(requestDto);
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverById(@PathVariable Long id) throws Exception {
        DriverResponseDTO driver = driverService.getDriverById(id);
        return ResponseEntity.ok(driver);
    }


    @GetMapping
    public ResponseEntity<List<DriverResponseDTO>> getAllDrivers() {
        List<DriverResponseDTO> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> updateDriver(@PathVariable Long id, @RequestBody DriverRequestDTO requestDto) throws Exception {
        DriverResponseDTO updatedDriver = driverService.updateDriver(id, requestDto);
        return ResponseEntity.ok(updatedDriver);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) throws Exception {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}
