package com.example.Uber_Backend.entities;

import com.example.Uber_Backend.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "ride")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private String pickupLocation;

    private String dropOffLocation;

    private LocalDateTime requestedAt;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    //Relationships

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = true)
    private Driver driver;
}
