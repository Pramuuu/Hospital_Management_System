package com.hospital.management.dto;

import lombok.Data;

@Data
public class StaffDTO {
    private Long id;
    private String name;
    private String role;
    private Long departmentId;
}