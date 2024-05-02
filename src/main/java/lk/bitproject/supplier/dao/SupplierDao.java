package lk.bitproject.supplier.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.bitproject.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.Query;

/**
 * This interface represents the data access object (DAO) for managing Supplier entities.
 * It extends the JpaRepository interface to inherit basic CRUD operations for Supplier entities.
 */
public interface SupplierDao extends JpaRepository<Supplier, Integer> {

    /**
     * Custom query to retrieve a Supplier entity by its name.
     * @param suppliername The name of the supplier to search for.
     * @return The Supplier entity matching the given name, or null if not found.
     */
    @Query(value = "select s from Supplier s where s.suppliername=?1")
    Supplier getBySupplierName(String suppliername);
}
