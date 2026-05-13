package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients") // https://localhost:4000/patients
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
    private final PatientService patientService;

    //  Jangan lupa gunkanan add constructor parameter ketika klik patientService
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //  Penggunaan GetMapping ini memberi tahu kalo create untuk handle semua get requests
    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();

        // disini kita akan return status code 200 dan body patients yang artinya akan berisi data patients
        return ResponseEntity.ok().body(patients);
    }

    //  Kalau mau create, pakai postmapping
    @PostMapping
    @Operation(summary = "Create a new Patient")

//    fungsi dari @Valid adalah perform validation ke patientrequestdto untuk mastiin
//    semua properti match dengan validation anotasi yang kita tambahin di patientrequestdto object
//    Anotasi itu yang ada di file PatientRequestDTO, yang @NotBlank, dll.

    // tapi sekarang di ganti ke @Validated, agar bisa disesuaikan dengan validator yang kita buat di dto
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Validated({Default.class, CreatePatientValidationGroup.class})
            @RequestBody PatientRequestDTO patientRequestDTO) {
            PatientResponseDTO patientResponseDTO = patientService.createPatient(
                    patientRequestDTO);

            return ResponseEntity.ok().body(patientResponseDTO);
        }

    // localhost:4000/patients/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Update a new Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,

        // kegunaan validated disini adalah untuk melakukan validasi dari semua defaults di dto
        @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
            PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<PatientResponseDTO> deletePatient(@PathVariable UUID id) {

        // yang dilakukan kode ini adalah memanggil patientService dan method delete Patient
        // yang kemudian return responseEntity dengan konten baru dan kode 204 (204 berarti no content) dan akan menyelesaikan request
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
