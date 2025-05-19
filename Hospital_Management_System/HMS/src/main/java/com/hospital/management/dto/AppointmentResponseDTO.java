package com.hospital.management.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentResponseDTO {
    private Long id;
    private LocalDateTime appointmentDate;
    private String status;
    private DoctorDTO doctor;
    private PatientDTO patient;
}