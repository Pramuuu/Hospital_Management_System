package com.hospital.management.controller;

import com.hospital.management.dto.ApiResponse;
import com.hospital.management.entity.Staff;
import com.hospital.management.service.impl.StaffServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffServiceImpl staffService;

    @GetMapping
    public ApiResponse<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffService.getAllStaff();
        return ApiResponse.success("Staff members retrieved successfully", staffList);
    }

    @GetMapping("/{id}")
    public ApiResponse<Staff> getStaffById(@PathVariable Long id) {
        Staff staff = staffService.getStaffById(id);
        return ApiResponse.success("Staff member retrieved successfully", staff);
    }

    @GetMapping("/department/{departmentId}")
    public ApiResponse<List<Staff>> getStaffByDepartmentId(@PathVariable Long departmentId) {
        List<Staff> staffList = staffService.getStaffByDepartmentId(departmentId);
        return ApiResponse.success("Department staff retrieved successfully", staffList);
    }

    @PostMapping
    public ApiResponse<Staff> createStaff(@RequestBody Staff staff) {
        Staff savedStaff = staffService.createStaff(staff);
        return ApiResponse.success("Staff member added successfully", savedStaff);
    }

    @PutMapping("/{id}")
    public ApiResponse<Staff> updateStaff(@PathVariable Long id, @RequestBody Staff staff) {
        Staff updatedStaff = staffService.updateStaff(id, staff);
        return ApiResponse.success("Staff member updated successfully", updatedStaff);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ApiResponse.success("Staff member deleted successfully", null);
    }
}