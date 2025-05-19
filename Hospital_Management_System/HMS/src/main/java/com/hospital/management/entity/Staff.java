package com.hospital.management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;

    @JsonBackReference(value="department-staff")
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}