package lk.bitproject.employee.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.bitproject.employee.dao.DesignationDao;
import lk.bitproject.employee.entity.Designation;

@RestController
public class DesignationController {

    @Autowired
    private DesignationDao dao;

    @GetMapping(value = "/designation/alldata", produces = "application/json")
    public List<Designation> getAllDataList() {
        return dao.findAll();
    }

}
