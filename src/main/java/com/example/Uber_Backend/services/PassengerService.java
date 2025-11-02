package com.example.Uber_Backend.services;

import com.example.Uber_Backend.dto.PassengerRequestDTO;
import com.example.Uber_Backend.dto.PassengerResponseDTO;
import com.example.Uber_Backend.entities.Passenger;
import com.example.Uber_Backend.mappers.PassengerMapper;
import com.example.Uber_Backend.repositories.IPassengerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PassengerService {

    private final IPassengerRepository passengerRepository;

    public PassengerResponseDTO createPassenger(PassengerRequestDTO requestDto) {
        Passenger passenger = PassengerMapper.toEntity(requestDto);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return PassengerMapper.toDto(savedPassenger);
    }

    public PassengerResponseDTO getPassengerById(Long id) throws Exception {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new Exception("Passenger not found with id: " + id));
        return PassengerMapper.toDto(passenger);
    }

    public List<PassengerResponseDTO> getAllPassengers() {
        return passengerRepository.findAll()
                .stream()
                .map(PassengerMapper::toDto)
                .collect(Collectors.toList());
    }

    public PassengerResponseDTO updatePassenger(Long id, PassengerRequestDTO requestDto) throws Exception {
        Passenger existingPassenger = passengerRepository.findById(id)
                .orElseThrow(() -> new Exception("Passenger not found with id: " + id));

        existingPassenger.setName(requestDto.getName());
        existingPassenger.setMobileNumber(requestDto.getMobileNumber());

        Passenger updatedPassenger = passengerRepository.save(existingPassenger);
        return PassengerMapper.toDto(updatedPassenger);
    }

    public void deletePassenger(Long id) throws Exception {
        if (!passengerRepository.existsById(id)) {
            throw new Exception("Passenger not found with id: " + id);
        }
        passengerRepository.deleteById(id);
    }
}
