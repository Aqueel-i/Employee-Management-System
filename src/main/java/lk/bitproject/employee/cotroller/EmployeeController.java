package lk.bitproject.employee.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lk.bitproject.employee.dao.EmployeeDao;
import lk.bitproject.employee.dao.EmployeeStatusDao;
import lk.bitproject.employee.entity.Employee;
import lk.bitproject.employee.entity.EmployeeStatus;

import java.util.List;

@RestController // implemented mapping available for use
// add implemented mapping to servrlrt container for use

public class EmployeeController {

   // create variable employee dao

   @Autowired // inject employee dao instance into dao variable

   private EmployeeDao dao;

   @Autowired // inject employee dao instance into dao variable

   private EmployeeStatusDao daoStatus;

   @RequestMapping(value = "/employee")
   public ModelAndView employeeUI() {

      // get logged User Authentication object using Security context holder
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      ModelAndView employeeview = new ModelAndView(); // for return ui
      employeeview.addObject("title", "Employee Management");
      employeeview.addObject("loggedUser", authentication.getName());
      employeeview.setViewName("employee.html");
      return employeeview;
   }

   @GetMapping(value = "/employee/alldata", produces = "application/json")
   public List<Employee> allEmployeeData() {
      return dao.findAll(); // dao variable eka use krl find all function eken data krgnnwa
   }

   // Post Mapping
   // define post mapping for save employee record
   @PostMapping(value = "/employee")
   public String saveEmployee(@RequestBody Employee employee) {

      // Autentication and Authorization

      // check duplicate NIC

      Employee extEmployeeByNic = dao.getByNic(employee.getNic());
      if (extEmployeeByNic != null) {
         // return "save not completed : Given NIC Already exist..!";

         return "save not Completed : Given " + employee.getNic() + "Already Excists...!";

      }

      // check duplicate Email

      Employee extEmployeeByEmail = dao.getByEmail(employee.getEmail());
      if (extEmployeeByEmail != null) {
         // return "save not completed : Given NIC Already exist..!";

         return "save not Completed : Given " + employee.getEmail() + "Already Excists...!";

      }

      try {

         // set auto generated value
         String nextNumber = dao.getNextEmployeeNumer();

         if (nextNumber == null) {
            employee.setEmpnum("00000001");
         } else {
            employee.setEmpnum(nextNumber);
         }

         // employee.setEmpnum("00000004");

         dao.save(employee);// save employee object (insert given employee object --> run insert query)

         return "OK";

      } catch (Exception e) {

         return "save not completed :" + e.getMessage();

      }
   }

   // delete mapping
   @DeleteMapping(value = "/employee")
   public String deleteEmployee(@RequestBody Employee employee) {
      // Authentication and authorization

      // Excisting
      // get excisting employee object getrferencebyid function --> used employee pk

      Employee extEmployee = dao.getReferenceById(employee.getId());

      if (extEmployee == null) {

         return "Delete Not Completed : Employee Does not Exist...!";

      }

      try {
         // hard delete
         // dao.delete(employee);
         // dao.delete(dao.getReferenceById(employee.getId()));

         // soft delete

         EmployeeStatus deleteStatus = daoStatus.getReferenceById(3);
         extEmployee.setEmployeestatus_id(deleteStatus);
         dao.save(extEmployee);

         // operation

         return "ok";
      } catch (Exception e) {

         return "Delete not Completed :" + e.getMessage();

      }
   }

   // put mapping
   @PutMapping(value = "/employee")
   public String updateEmployee(@RequestBody Employee employee) {

      // Authentication and authorization

      // Duplicate and excisting

      Employee extEmployee = dao.getReferenceById(employee.getId());
      if (extEmployee == null) {
         return "Update not Completed:Employee Not Available...!";
      }

      Employee extEmployeeByNic = dao.getByNic(employee.getNic());
      if (extEmployeeByNic != null && extEmployeeByNic.getId() != employee.getId()) {
         return "Update Not Completed : Changed " + employee.getNic() + "already Excists..!";

      }

      Employee extEmployeeByEmail = dao.getByEmail(employee.getEmail());
      if (extEmployeeByEmail != null && extEmployeeByEmail.getId() != employee.getId()) {
         return "Update Not Completed : Changed " + employee.getEmail() + "already Excists..!";

      }

      try {
         // set auto set value

         // operator
         dao.save(employee);

         // dependencies

         return "OK";
      } catch (Exception e) {
         return "Update Not Completed :" + e.getMessage();
      }
   }

   // create mapping for get employee list withot having User Account
   // URL[/employee/listwithoutuseraccount]
   @GetMapping(value = "/employee/listwithoutuseraccount", produces = "application/json")
   public List<Employee> getEmployeeListWithoutUserAccount() {
      return dao.getListByWithoutUserAccount();

   }
}
