package com.pm.billingservice.grpc;

// BillingServiceImplBase dari target/generated-sources/protobuf/grpc-java/billing/BillingServiceGrpc
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    // kita implement grpc server stub
    @Override // disini kita overriding createBillingAccount method yang di generate di ImplBase class kita
    public void createBillingAccount(billing.BillingRequest billingRequest,
         StreamObserver<billing.BillingResponse> responseObserver) {
        // kegunaan StreamObserver untuk mendapatkan multiple respons dalam waktu yang sama dan nerima respons
        // response back and forth dari klien yang sama

        log.info("createBillingAccount request received {}", billingRequest.toString());

        // Bussiness logic (save ke db, perform calculates, etc.)

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("ACTIVE")
                .build();

        // return response, respons 1 untuk ngirim ke klien (patient-service) respons 2 untuk akhirin cycle.
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
