package lk.bitproject.supplier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lk.bitproject.privilege.cotroller.PrivilegeController;
import lk.bitproject.privilege.entity.Privilege;
import lk.bitproject.supplier.dao.SupplierDao;
import lk.bitproject.supplier.entity.Supplier;
import lk.bitproject.user.dao.UserDao;
import lk.bitproject.user.entity.User;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SupplierController {

    @Autowired
    private SupplierDao dao;

    @Autowired
    private UserDao daoUser;

    // Create PrivilegeController Instance
    private PrivilegeController privilegeController = new PrivilegeController();

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

    @GetMapping(value = "/supplier/alldata", produces = "application/json")
    public List<Supplier> allEmployeeData() {
        return dao.findAll();
    }

    @PostMapping(value = "/supplier")
    public String postMethodName(@RequestBody Supplier supplier) {
        // Authentication and Authorization
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // get privilege object using getPrivilegeByUserModule function
        Privilege userPrivilege = privilegeController.getPrivilegeByUserModule(authentication.getName(), "Supplier");
        if (!userPrivilege.getInspriv()) {
            return "Save not Completed! You don't have permission to...";
        }

        // Check Duplicate
        Supplier extSupplierByName = dao.getBySupplierName(supplier.getSuppliername());
        if (extSupplierByName != null) {
            return "save not completed : Given NIC Already exist..!";
        }

        try {
            supplier.setAddeddatetime(LocalDateTime.now());
            User loggedUser = daoUser.getByUsername(authentication.getName());
            supplier.setAddeduser_id(loggedUser.getId());

            supplier.setRegno(null);

            dao.save(supplier);
            return "OK";

        } catch (Exception e) {
            return "save not completed :" + e.getMessage();
        }
    }
}
