package com.hospital.management.service.impl;

import com.hospital.management.entity.Department;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl {
    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = getDepartmentById(id);
        department.setName(departmentDetails.getName());
        department.setHeadOfDepartment(departmentDetails.getHeadOfDepartment());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        Department department = getDepartmentById(id);
        departmentRepository.delete(department);
    }
}
