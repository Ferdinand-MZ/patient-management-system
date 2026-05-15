package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patients.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    // kafka listener berguna untuk memberi tahu identitas si kafka consumer ke kafka broker
    @KafkaListener(topics = "patient", groupId = "analytics-service")
    // karena kan emang message kita di convert ke byte sebelumnya
    public void consumeEvent(byte[] event) {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            // ... isi dengan any bussiness logic yang berhubungan dengan analytics

            log.info("Received Patient Event: [PatientId={}, PatientName={}, PatientEmail={}]",
                patientEvent.getPatientId(), patientEvent.getName(), patientEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing event {}", e.getMessage());
        }
    }
}
