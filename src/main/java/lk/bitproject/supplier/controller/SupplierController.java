package lk.bitproject.supplier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lk.bitproject.supplier.dao.SupplierDao;
import lk.bitproject.supplier.entity.Supplier;

import java.util.*;

@RestController
public class SupplierController {

    @Autowired
    private SupplierDao dao;

    @RequestMapping(value = "/supplier")
    public ModelAndView supplierUI() {

        // get logged User Authentication object using Security context holder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView supplierview = new ModelAndView(); // for return ui
        supplierview.addObject("title", "Supplier Management");
        supplierview.addObject("loggedUser", authentication.getName());
        supplierview.setViewName("supplier.html");
        return supplierview;
    }

    @GetMapping(value  = "/supplier/alldata", produces = "application/json")
    public List<Supplier> allEmployeeData() {
        return dao.findAll();
    }

}
