package lk.bitproject.supplier.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.bitproject.supplier.entity.Supplier;

public interface SupplierDao extends JpaRepository<Supplier, Integer> {

}
