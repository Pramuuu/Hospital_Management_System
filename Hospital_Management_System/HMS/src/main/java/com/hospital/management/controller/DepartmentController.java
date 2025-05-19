package com.hospital.management.controller;

import com.hospital.management.dto.ApiResponse;
import com.hospital.management.entity.Department;
import com.hospital.management.service.impl.DepartmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentServiceImpl departmentService;

    @GetMapping
    public ApiResponse<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ApiResponse.success("Departments retrieved successfully", departments);
    }

    @GetMapping("/{id}")
    public ApiResponse<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return ApiResponse.success("Department retrieved successfully", department);
    }

    @PostMapping
    public ApiResponse<Department> createDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.createDepartment(department);
        return ApiResponse.success("Department added successfully", savedDepartment);
    }

    @PutMapping("/{id}")
    public ApiResponse<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Department updatedDepartment = departmentService.updateDepartment(id, department);
        return ApiResponse.success("Department updated successfully", updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ApiResponse.success("Department deleted successfully", null);
    }
}
