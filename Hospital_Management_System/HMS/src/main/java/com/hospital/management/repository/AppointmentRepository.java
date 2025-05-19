package com.hospital.management.repository;

import com.hospital.management.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Find appointments by patient ID
    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId")
    List<Appointment> findByPatientId(@Param("patientId") Long patientId);

    // Find appointments by doctor ID
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Appointment> findByDoctorId(@Param("doctorId") Long doctorId);

    // Find overlapping appointments for a doctor
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId " +
           "AND a.appointmentDate BETWEEN :startTime AND :endTime " +
           "AND a.status != 'CANCELLED'")
    List<Appointment> findOverlappingAppointments(
        @Param("doctorId") Long doctorId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
}