// File: src/main/java/com/hospital/management/service/impl/AppointmentServiceImpl.java
package com.hospital.management.service.impl;

import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.Doctor;
import com.hospital.management.entity.Patient;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        // Verify patient exists
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        // Verify doctor exists
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public Appointment createAppointment(Appointment appointment) {
        // Validate appointment time
        LocalDateTime appointmentTime = appointment.getAppointmentDate();
        if (appointmentTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Appointment time must be in the future");
        }

        // Validate doctor exists
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " +
                        appointment.getDoctor().getId()));

        // Validate patient exists
        Patient patient = patientRepository.findById(appointment.getPatient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " +
                        appointment.getPatient().getId()));

        // Set appointment duration (30 minutes)
        LocalDateTime startTime = appointment.getAppointmentDate();
        LocalDateTime endTime = startTime.plusMinutes(30);

        // Check for overlapping appointments
        List<Appointment> overlappingAppointments = appointmentRepository
                .findOverlappingAppointments(doctor.getId(), startTime, endTime);

        if (!overlappingAppointments.isEmpty()) {
            throw new RuntimeException("Doctor has another appointment scheduled between " +
                    startTime + " and " + endTime);
        }

        // Set references and status
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStatus("SCHEDULED");

        // Save and return
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Appointment existingAppointment = getAppointmentById(id);

        // If appointment date is being updated, check for conflicts
        if (appointmentDetails.getAppointmentDate() != null) {
            LocalDateTime newStartTime = appointmentDetails.getAppointmentDate();

            // Validate new appointment time
            if (newStartTime.isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Appointment time must be in the future");
            }

            LocalDateTime newEndTime = newStartTime.plusMinutes(30);

            List<Appointment> overlappingAppointments = appointmentRepository
                    .findOverlappingAppointments(
                        existingAppointment.getDoctor().getId(),
                        newStartTime,
                        newEndTime
                    );

            // Remove current appointment from the list if it's found
            overlappingAppointments.removeIf(apt -> apt.getId().equals(id));

            if (!overlappingAppointments.isEmpty()) {
                throw new RuntimeException("Cannot update appointment. Doctor has another appointment scheduled between " +
                        newStartTime + " and " + newEndTime);
            }

            existingAppointment.setAppointmentDate(newStartTime);
        }

        // Update status if provided
        if (appointmentDetails.getStatus() != null && !appointmentDetails.getStatus().isEmpty()) {
            existingAppointment.setStatus(appointmentDetails.getStatus());
        }

        // Update doctor if provided
        if (appointmentDetails.getDoctor() != null && appointmentDetails.getDoctor().getId() != null) {
            Doctor newDoctor = doctorRepository.findById(appointmentDetails.getDoctor().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " +
                            appointmentDetails.getDoctor().getId()));
            existingAppointment.setDoctor(newDoctor);
        }

        // Update patient if provided
        if (appointmentDetails.getPatient() != null && appointmentDetails.getPatient().getId() != null) {
            Patient newPatient = patientRepository.findById(appointmentDetails.getPatient().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " +
                            appointmentDetails.getPatient().getId()));
            existingAppointment.setPatient(newPatient);
        }

        return appointmentRepository.save(existingAppointment);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);
    }
}