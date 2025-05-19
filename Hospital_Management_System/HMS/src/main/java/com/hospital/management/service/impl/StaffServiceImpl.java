package com.hospital.management.service.impl;

import com.hospital.management.entity.Department;
import com.hospital.management.entity.Staff;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.repository.DepartmentRepository;
import com.hospital.management.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl {
    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(Long id) {
        return staffRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Staff member not found with id: " + id));
    }

    public List<Staff> getStaffByDepartmentId(Long departmentId) {
        return staffRepository.findByDepartmentId(departmentId);
    }

    public Staff createStaff(Staff staff) {
        Department department = departmentRepository.findById(staff.getDepartment().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        staff.setDepartment(department);
        return staffRepository.save(staff);
    }

    public Staff updateStaff(Long id, Staff staffDetails) {
        Staff staff = getStaffById(id);
        staff.setName(staffDetails.getName());
        staff.setRole(staffDetails.getRole());
        if (staffDetails.getDepartment() != null) {
            Department department = departmentRepository.findById(staffDetails.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            staff.setDepartment(department);
        }
        return staffRepository.save(staff);
    }

    public void deleteStaff(Long id) {
        Staff staff = getStaffById(id);
        staffRepository.delete(staff);
    }
}