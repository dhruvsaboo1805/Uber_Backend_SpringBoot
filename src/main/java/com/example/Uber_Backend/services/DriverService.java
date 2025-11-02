package com.example.Uber_Backend.services;

import com.example.Uber_Backend.dto.DriverRequestDTO;
import com.example.Uber_Backend.dto.DriverResponseDTO;
import com.example.Uber_Backend.entities.Driver;
import com.example.Uber_Backend.mappers.DriverMapper;
import com.example.Uber_Backend.repositories.IDriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DriverService {
    private final IDriverRepository driverRepository;

    public DriverResponseDTO createDriver(DriverRequestDTO requestDto) {
        Driver driver = DriverMapper.toEntity(requestDto);
        Driver savedDriver = driverRepository.save(driver);
        return DriverMapper.toDto(savedDriver);
    }

    public DriverResponseDTO getDriverById(Long id) throws Exception {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new Exception("Driver not found with id: " + id));
        return DriverMapper.toDto(driver);
    }

    public List<DriverResponseDTO> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(DriverMapper::toDto)
                .collect(Collectors.toList());
    }

    public DriverResponseDTO updateDriver(Long id, DriverRequestDTO requestDto) throws Exception {
        Driver existingDriver = driverRepository.findById(id)
                .orElseThrow(() -> new Exception("Driver not found with id: " + id));

        existingDriver.setName(requestDto.getName());

        Driver updatedDriver = driverRepository.save(existingDriver);
        return DriverMapper.toDto(updatedDriver);
    }


    public void deleteDriver(Long id) throws Exception {
        if (!driverRepository.existsById(id)) {
            throw new Exception("Driver not found with id: " + id);
        }
        driverRepository.deleteById(id);
    }
}
