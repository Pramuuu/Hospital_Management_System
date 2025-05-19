package com.hospital.management.repository;

import com.hospital.management.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findByDepartmentId(Long departmentId);
}