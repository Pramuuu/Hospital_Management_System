package com.hospital.management.controller;

import com.hospital.management.dto.ApiResponse;
import com.hospital.management.entity.Patient;
import com.hospital.management.service.impl.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientServiceImpl patientService;

    @GetMapping
    public ApiResponse<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ApiResponse.success("Patients retrieved successfully", patients);
    }

    @GetMapping("/{id}")
    public ApiResponse<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return ApiResponse.success("Patient retrieved successfully", patient);
    }

    @PostMapping
    public ApiResponse<Patient> createPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.createPatient(patient);
        return ApiResponse.success("Patient added successfully", savedPatient);
    }

    @PutMapping("/{id}")
    public ApiResponse<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(id, patient);
        return ApiResponse.success("Patient updated successfully", updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ApiResponse.success("Patient deleted successfully", null);
    }
}