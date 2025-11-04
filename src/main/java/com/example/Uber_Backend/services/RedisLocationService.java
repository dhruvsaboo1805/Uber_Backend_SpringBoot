package com.example.Uber_Backend.services;

import com.example.Uber_Backend.dto.DriverLocationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisLocationService {
    private static final String DRIVER_GEO_OPS_KEY = "driver:geo";

    private final StringRedisTemplate stringRedisTemplate;

    public Boolean saveDriverLocation(String driverId , Double latitude, Double longitude){
        GeoOperations<String , String> geoOperations = stringRedisTemplate.opsForGeo();
        geoOperations.add(DRIVER_GEO_OPS_KEY ,
                new RedisGeoCommands.GeoLocation<>(driverId , new Point(longitude, latitude)));
        return true;
    }

    public List<DriverLocationDTO> getNearByDrivers(Double latitude, Double longitude , Double radius){
        GeoOperations<String , String> geoOperations = stringRedisTemplate.opsForGeo();
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        Circle circle = new Circle(new Point(longitude, latitude), radius);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOperations.radius(DRIVER_GEO_OPS_KEY , circle);
        List<DriverLocationDTO> driverLocations = new ArrayList<>();
        for(GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {

            Point point = geoOperations.position(DRIVER_GEO_OPS_KEY, result.getContent().getName()).get(0); // location of individual driver in redis

            DriverLocationDTO driverLocation = DriverLocationDTO.builder()
                    .driverId(result.getContent().getName())
                    .latitude(point.getY())
                    .longitude(point.getX())
                    .build();

            driverLocations.add(driverLocation);
        }

        return driverLocations;
    }
}
