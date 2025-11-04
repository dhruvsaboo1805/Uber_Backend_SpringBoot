package com.example.Uber_Backend.controllers;

import com.example.Uber_Backend.dto.DriverLocationDTO;
import com.example.Uber_Backend.services.RedisLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {
    private final RedisLocationService locationService;

    @PostMapping("/driverLocation")
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody DriverLocationDTO driverLocation) {
        Boolean saved = locationService.saveDriverLocation(driverLocation.getDriverId(), driverLocation.getLatitude(), driverLocation.getLongitude());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/nearbyDrivers")
    public ResponseEntity<List<DriverLocationDTO>> getNearbyDrivers(@RequestParam Double lat, @RequestParam Double lon , @RequestParam Double radius) {
        List<DriverLocationDTO> nearbyDrivers = locationService.getNearByDrivers(lat, lon, radius);
        return ResponseEntity.ok(nearbyDrivers);
    }
}
