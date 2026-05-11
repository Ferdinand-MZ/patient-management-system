package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    // membuat email unique, dengan cara kode ini akan memberi tahu jpa kita apakah email ini sudah exists apa belum
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);
}
