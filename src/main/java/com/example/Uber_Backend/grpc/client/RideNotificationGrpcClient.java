package com.example.Uber_Backend.grpc.client;

import com.example.Uber_Backend.grpc.RideNotificationRequest;
import com.example.Uber_Backend.grpc.RideNotificationResponse;
import com.example.Uber_Backend.grpc.RideNotificationServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RideNotificationGrpcClient {

    @GrpcClient("websocket-service")
    private RideNotificationServiceGrpc.RideNotificationServiceBlockingStub blockingStub;

    public void notifyDriversForRide(String bookingId, List<String> driverIds,
                                     Double pickupLat, Double pickupLon) {

        RideNotificationRequest request = RideNotificationRequest.newBuilder()
                .setBookingId(bookingId)
                .addAllDriverIds(driverIds)
                .setPickupLatitude(pickupLat)
                .setPickupLongitude(pickupLon)
                .build();

        RideNotificationResponse response = blockingStub.notifyDrivers(request);

        if (!response.getSuccess()) {
            throw new RuntimeException("Failed to notify drivers: " + response.getMessage());
        }
    }
}
