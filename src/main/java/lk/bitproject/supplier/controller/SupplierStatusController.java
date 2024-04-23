package lk.bitproject.supplier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.bitproject.supplier.dao.SupplierStatusDao;
import lk.bitproject.supplier.entity.SupplierStatus;

import java.util.List;

@RestController
public class SupplierStatusController {

    @Autowired
    private SupplierStatusDao dao;

    @GetMapping(value = "/supplierstatus/alldata", produces = "application/json")
    public List<SupplierStatus> getAllData() {
        return dao.findAll();
    }

}
