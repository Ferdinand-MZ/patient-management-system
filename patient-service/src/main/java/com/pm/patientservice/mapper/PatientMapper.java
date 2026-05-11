package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
     PatientResponseDTO patientDTO = new PatientResponseDTO();
     patientDTO.setId(patient.getId().toString());
     patientDTO.setName(patient.getName());
     patientDTO.setEmail(patient.getEmail());
     patientDTO.setAddress(patient.getAddress());
     patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

     return patientDTO;
    }

    // Mengubah patient entity menjadi model
    public static Patient toModel(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());

        // ini ga bisa, karena yang di expect itu Local Date tetapi yang disini merupakan string
        // patient.setDateOfBirth(patientRequestDTO.getDateofBirth());
        // harus di parse dulu

        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        return patient;

    }
}
