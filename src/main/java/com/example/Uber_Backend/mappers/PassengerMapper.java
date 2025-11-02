package com.example.Uber_Backend.mappers;

import com.example.Uber_Backend.dto.PassengerRequestDTO;
import com.example.Uber_Backend.dto.PassengerResponseDTO;
import com.example.Uber_Backend.entities.Passenger;

public class PassengerMapper {
    public static Passenger toEntity(PassengerRequestDTO dto) {
        Passenger passenger = new Passenger().builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .mobileNumber(dto.getMobileNumber())
                .build();
        return passenger;
    }

    public static PassengerResponseDTO toDto(Passenger passenger) {
        PassengerResponseDTO passengerResponseDTO = new PassengerResponseDTO().builder()
                .id(passenger.getId())
                .name(passenger.getName())
                .email(passenger.getEmail())
                .mobileNumber(passenger.getMobileNumber())
                .build();
        return passengerResponseDTO;
    }
}
