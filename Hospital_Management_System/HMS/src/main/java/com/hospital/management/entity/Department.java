package com.hospital.management.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String headOfDepartment;

    @JsonManagedReference(value="department-staff")
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Staff> staff;
}