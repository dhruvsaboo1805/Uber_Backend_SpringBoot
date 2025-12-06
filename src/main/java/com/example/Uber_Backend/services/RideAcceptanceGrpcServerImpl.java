package com.example.Uber_Backend.services;

import com.example.Uber_Backend.*;
import com.example.Uber_Backend.dto.RideResponseDTO;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RideAcceptanceGrpcServerImpl extends RideNotificationServiceGrpc.RideNotificationServiceImplBase {

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
                    .setBookingId(response.getId())
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

    // todo will do after class not able to do
    @Override
    public void notifyDrivers(RideNotificationRequest request , StreamObserver<RideNotificationResponse> responseObserver) {

    }
}
