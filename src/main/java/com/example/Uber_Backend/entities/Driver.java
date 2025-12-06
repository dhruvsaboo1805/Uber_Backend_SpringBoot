package com.example.Uber_Backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "driver")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @NotEmpty
    @Size(max = 10 , message = "Mobile number should not be empty")
    private String mobileNumber;

//    private String currentLocation;
    @Builder.Default
    private boolean isAvailable = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // relationships
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    List<RideBooking> rideBookings;

}
