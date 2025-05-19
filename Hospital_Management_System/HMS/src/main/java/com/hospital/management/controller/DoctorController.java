package com.hospital.management.controller;

import com.hospital.management.dto.ApiResponse;
import com.hospital.management.entity.Doctor;
import com.hospital.management.service.impl.DoctorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorServiceImpl doctorService;

    @GetMapping
    public ApiResponse<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ApiResponse.success("Doctors retrieved successfully", doctors);
    }

    @GetMapping("/{id}")
    public ApiResponse<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return ApiResponse.success("Doctor retrieved successfully", doctor);
    }

    @PostMapping
    public ApiResponse<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.createDoctor(doctor);
        return ApiResponse.success("Doctor added successfully", savedDoctor);
    }

    @PutMapping("/{id}")
    public ApiResponse<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        return ApiResponse.success("Doctor updated successfully", updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ApiResponse.success("Doctor deleted successfully", null);
    }
}