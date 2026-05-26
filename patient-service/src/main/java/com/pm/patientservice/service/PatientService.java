package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

//    Buat Constructor untuk patient service class
    public PatientService(PatientRepository patientRepository,
          BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }
//    Pattern tadi disebut dependency injection

    public List<PatientResponseDTO> getPatients () {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()

        //     kode dibawah ini kepanjangan
        //      .map(patient -> PatientMapper.toDTO(patient)).toList();

        //  kode standar lambda, tapi lebih mudah dibaca dan melakukan hal yang sama
                .map(PatientMapper::toDTO).toList();


    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        // ngecek apakah email yang dikirim sudah pernah dibuat? datanya dari patientRepository
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {

            // ini custom exception buatan kita
            throw new EmailAlreadyExistsException("A patient with this email" + "already exists " + patientRequestDTO.getEmail());
        }

        // disini kita ga bisa pakai patientRequestDTO sebagai parameter karena type nya ga kompatibel (string semua)
        //  Patient newPatient = patientRepository.save(patientRequestDTO)
        // jadi harus convert patient request dto kita jadi patient entity model object

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getName(),
                newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        // ambil data patient yang mau kita update terlebih dahulu
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email" + "already exists " + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        // registered Date kita tidak masukkan karena kita tidak allow registered date di alter datanya
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    // kenapa void? karena ga akan ada hal yang mau di return disini
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }


}
