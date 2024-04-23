package lk.bitproject.supplier.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.bitproject.supplier.entity.SupplierStatus;

public interface SupplierStatusDao extends JpaRepository<SupplierStatus,Integer> {
    
}