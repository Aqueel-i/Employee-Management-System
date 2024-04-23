package lk.bitproject.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.bitproject.employee.entity.Designation;

public interface DesignationDao extends JpaRepository<Designation , Integer>{
    
}
