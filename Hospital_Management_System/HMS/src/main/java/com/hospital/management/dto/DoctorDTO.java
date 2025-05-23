package com.hospital.management.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long id;
    private String name;
    private String specialization;
    private String contact;
}