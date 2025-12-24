package com.example.Uber_Backend.client;

import com.example.Uber_Backend.RideNotificationRequest;
import com.example.Uber_Backend.RideNotificationResponse;
import com.example.Uber_Backend.RideNotificationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class GrpcClient {
    @Value("${grpc.client.port:9091}")
    private int grpcClientPort;

    @Value("${grpc.client.host:localhost}")
    private String grpcClientHost;

    private ManagedChannel channel;
    private RideNotificationServiceGrpc.RideNotificationServiceBlockingStub rideNotificationStub;

    @PostConstruct
    public void init() {
        channel = ManagedChannelBuilder.forAddress(grpcClientHost, grpcClientPort)
                .usePlaintext()
                .build();

        rideNotificationStub = RideNotificationServiceGrpc.newBlockingStub(channel);
    }

    public boolean NotifyDrivers(String pickUpLocationLatitude, String pickUpLocationLongitude, Integer bookingId, List<Integer> driverIds) {
        RideNotificationRequest request = RideNotificationRequest.newBuilder()
                .setPickupLatitude(Double.parseDouble(pickUpLocationLatitude))
                .setPickupLongitude(Double.parseDouble(pickUpLocationLongitude))
                .setBookingId(Long.parseLong(String.valueOf(bookingId)))
                .addAllDriverIds(Collections.singleton(Long.valueOf(driverIds.toString())))
                .build();

        RideNotificationResponse response = rideNotificationStub.notifyDrivers(request); // make the rpc calls

        return response.getSuccess();
    }
}
