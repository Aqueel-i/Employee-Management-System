package lk.bitproject.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.bitproject.employee.entity.EmployeeStatus;

public interface EmployeeStatusDao extends JpaRepository<EmployeeStatus, Integer> {
    
}