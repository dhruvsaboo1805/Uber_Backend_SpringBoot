package com.example.Uber_Backend.grpc.client.server;

import com.example.Uber_Backend.dto.RideResponseDTO;
import com.example.Uber_Backend.grpc.RideAcceptanceRequest;
import com.example.Uber_Backend.grpc.RideAcceptanceResponse;
import com.example.Uber_Backend.grpc.RideNotificationServiceGrpc;
import com.example.Uber_Backend.services.RideService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class RideAcceptanceGrpcServer extends RideNotificationServiceGrpc.RideNotificationServiceImplBase {

    private final RideService rideService;

    @Override
    public void confirmRideAcceptance(RideAcceptanceRequest request,
                                      StreamObserver<RideAcceptanceResponse> responseObserver) {
        try {
            RideResponseDTO response = rideService.confirmRideAcceptance(
                    request.getBookingId(),
                    request.getDriverId()
            );

            RideAcceptanceResponse grpcResponse = RideAcceptanceResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Ride accepted successfully")
                    .setBookingId(response.getBookingId())
                    .build();

            responseObserver.onNext(grpcResponse);
            responseObserver.onCompleted();

        } catch (Exception e) {
            RideAcceptanceResponse grpcResponse = RideAcceptanceResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage(e.getMessage())
                    .build();

            responseObserver.onNext(grpcResponse);
            responseObserver.onCompleted();
        }
    }
}
