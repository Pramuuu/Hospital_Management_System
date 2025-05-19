package com.hospital.management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentDate;
    private String status;

    @JsonBackReference(value="patient-appointment")
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @JsonBackReference(value="doctor-appointment")
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}