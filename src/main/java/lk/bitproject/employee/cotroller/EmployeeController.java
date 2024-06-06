package lk.bitproject.employee.cotroller;

import lk.bitproject.privilege.cotroller.PrivilegeController;
import lk.bitproject.privilege.entity.Privilege;
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

import java.util.ArrayList;
import java.util.List;

@RestController // implemented mapping available for use
// add implemented mapping to servrlrt container for use

public class EmployeeController {

   // create variable employee dao

   @Autowired // inject employee dao instance into dao variable

   private EmployeeDao dao;

   @Autowired // inject employee dao instance into dao variable

   private EmployeeStatusDao daoStatus;

   // Create privilege Controller Instance
   private PrivilegeController privilegeController = new PrivilegeController();

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
      // return "Data";
      // get logged User Authentication object using Security context holder
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      // get privilege object using getPrivilegeByUserModule function
      Privilege userPrivilege = privilegeController.getPrivilegeByUserModule(authentication.getName(), "Employee");
      if (!userPrivilege.getSelpriv()) {
         return new ArrayList<Employee>();
      }
      return dao.findAll(); // dao variable eka use krl find all function eken data krgnnwa
   }

   // Post Mapping
   // define post mapping for save employee record
   @PostMapping(value = "/employee")
   public String saveEmployee(@RequestBody Employee employee) {

      // Authentication and Authorization
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      // get privilege object using getPrivilegeByUserModule function
      Privilege userPrivilege = privilegeController.getPrivilegeByUserModule(authentication.getName(), "Employee");
      if (!userPrivilege.getInspriv()) {
         return "Save not Completed! You don't have permission to...";
      }

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

   // Delete mapping
   @DeleteMapping(value = "/employee")
   public String deleteEmployee(@RequestBody Employee employee) {
      // Authentication and authorization
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Privilege userPrivilege = privilegeController.getPrivilegeByUserModule(authentication.getName(), "Employee");
      if (!userPrivilege.getInspriv()) {
         return "Delete not Completed! You don't have permission to...";
      }
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
   /**
    * Update an employee entity.
    *
    * @param employee The Employee object containing updated information.
    * @return A message indicating whether the update was successful or not.
    */
   @PutMapping(value = "/employee")
   public String updateEmployee(@RequestBody Employee employee) {

      // Authentication and authorization
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Privilege userPrivilege = privilegeController.getPrivilegeByUserModule(authentication.getName(), "Employee");
      if (!userPrivilege.getInspriv()) {
         return "Update not Completed! You don't have permission to...";
      }

      // Duplicate and existing
      // Check if the employee exists
      Employee extEmployee = dao.getReferenceById(employee.getId());
      if (extEmployee == null) {
         return "Update not Completed:Employee Not Available...!";
      }

      // Check for duplicate NIC
      Employee extEmployeeByNic = dao.getByNic(employee.getNic());
      if (extEmployeeByNic != null && extEmployeeByNic.getId() != employee.getId()) {
         return "Update Not Completed : Changed " + employee.getNic() + "already Excists..!";

      }

      // Check for duplicate email
      Employee extEmployeeByEmail = dao.getByEmail(employee.getEmail());
      if (extEmployeeByEmail != null && extEmployeeByEmail.getId() != employee.getId()) {
         return "Update Not Completed : Changed " + employee.getEmail() + "already Excists..!";

      }

      try {
         // set auto set value

         // operator
         dao.save(employee); // Save the updated employee

         // dependencies

         return "OK"; // Update successful
      } catch (Exception e) {
         return "Update Not Completed :" + e.getMessage(); // Update unsuccessful
      }
   }

   // create mapping for get employee list withot having User Account
   // URL[/employee/listwithoutuseraccount]
   @GetMapping(value = "/employee/listwithoutuseraccount", produces = "application/json")
   public List<Employee> getEmployeeListWithoutUserAccount() {
      return dao.getListByWithoutUserAccount();

   }
}