package com.hospital.management.controller;

import com.hospital.management.dto.AppointmentResponseDTO;
import com.hospital.management.dto.DoctorDTO;
import com.hospital.management.dto.PatientDTO;
import com.hospital.management.entity.Appointment;
import com.hospital.management.service.impl.AppointmentServiceImpl;
import com.hospital.management.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentServiceImpl appointmentService;

    private AppointmentResponseDTO convertToDTO(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStatus(appointment.getStatus());

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(appointment.getDoctor().getId());
        doctorDTO.setName(appointment.getDoctor().getName());
        doctorDTO.setSpecialization(appointment.getDoctor().getSpecialization());
        doctorDTO.setContact(appointment.getDoctor().getContact());
        dto.setDoctor(doctorDTO);

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(appointment.getPatient().getId());
        patientDTO.setName(appointment.getPatient().getName());
        patientDTO.setContact(appointment.getPatient().getContact());
        dto.setPatient(patientDTO);

        return dto;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse<List<AppointmentResponseDTO>> getAllAppointments() {
        List<AppointmentResponseDTO> appointments = appointmentService.getAllAppointments()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ApiResponse.success("Appointments retrieved successfully", appointments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse<AppointmentResponseDTO> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ApiResponse.success("Appointment retrieved successfully", convertToDTO(appointment));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse<List<AppointmentResponseDTO>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByPatientId(patientId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ApiResponse.success("Patient appointments retrieved successfully", appointments);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse<List<AppointmentResponseDTO>> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ApiResponse.success("Doctor appointments retrieved successfully", appointments);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse<AppointmentResponseDTO> createAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment savedAppointment = appointmentService.createAppointment(appointment);
            return ApiResponse.success("Appointment scheduled successfully", convertToDTO(savedAppointment));
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse<AppointmentResponseDTO> updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment appointment) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
            return ApiResponse.success("Appointment updated successfully", convertToDTO(updatedAppointment));
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ApiResponse.success("Appointment cancelled successfully", null);
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}