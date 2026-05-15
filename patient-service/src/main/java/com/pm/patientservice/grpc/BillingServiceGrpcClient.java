package com.pm.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    // dibawah ini merupakan nested class di dalam billing service grpc class yang memberikan blocking
    // atau bisa disebut synchronous client calls ke grpc server yang berjalan di billing service.

    // jadi setiap kali kita melakukan panggilan ke billing service dengan blockingstub ini
    // maka ekseksui nya akan menunggu untuk respons kembali dari billing service sebelum melanjutkan lagi
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;


    // localhost:9001/BillingService/CreatePatientAccount <- kalo local dev gini
    // aws.grpc:123123/BillingService/CreatePatientAccount <- kalo di taruh aws/deploy
    public BillingServiceGrpcClient(
        @Value("${billing.service.address:localhost}") String serverAddress,
        @Value("${billing.service.grpc.port:9001}") int serverPort
    ) {

        log.info("Connecting to Billing Service GRPC Service at {}:{}", serverAddress, serverPort);

        // initialize blocking stub
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
                .usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email) {

        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email).build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Receive response from billing service via gRPC: {}", response);

        return response;
    }
}
