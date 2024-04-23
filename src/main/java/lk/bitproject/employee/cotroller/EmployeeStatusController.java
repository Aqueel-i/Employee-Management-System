package lk.bitproject.employee.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.bitproject.employee.dao.EmployeeStatusDao;
import lk.bitproject.employee.entity.EmployeeStatus;

@RestController
public class EmployeeStatusController {

    @Autowired
    private EmployeeStatusDao dao;

    //url = "/employeestatus/alldata"
    @GetMapping(value = "/employeestatus/alldata", produces = "application/json")
    public List<EmployeeStatus> getAllDataList() {
        return dao.findAll();
    }

}
