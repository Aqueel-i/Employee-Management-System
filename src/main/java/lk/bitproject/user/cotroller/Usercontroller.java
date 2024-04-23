package lk.bitproject.user.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

import lk.bitproject.user.dao.UserDao;
import lk.bitproject.user.entity.User;
import org.springframework.web.bind.annotation.PutMapping;

@RestController

public class Usercontroller {

    @Autowired // add dependancy for dao variable
    private UserDao dao; // create variable dao

    // Dependancy injection
    /*
     * public Usercontroller(UserDao dao) {
     * this.dao = dao;
     * }
     */

    @RequestMapping(value = "/user")

    public ModelAndView userUI() {

        // get logged User Authentication object using Security context holder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView userview = new ModelAndView(); // for return ui
        userview.addObject("title", "User Management");
        userview.addObject("loggedUser", authentication.getName());
        userview.setViewName("user.html");
        return userview;
    }

    // create mapping for get all user data [user/alldata]
    @RequestMapping(value = "/user/alldata", produces = "application/json")
    public List<User> getAllData() { // define function for return mapping output

        return dao.findAll();

    }

    // Post Mapping
    @PostMapping(value = "/user")
    public String insertUser(@RequestBody User user) {
        // Authentication and Athorization

        // Duplicate and excisting

        // Check whether Email already Excisting

        User extUserByEmail = dao.getByEmail(user.getEmail());
        if (extUserByEmail != null) {
            return "Email Already Excists..!";
        }

        // Check whether Email already Excisting
        User extUserByEmployee = dao.getByEmployee(user.getEmployee_id().getId());
        if (extUserByEmployee != null) {
            return "Selected Employee Already Has a useraccount..!";
        }

        try {

            // Set Auto generated value
            user.setAdded_datetime(LocalDateTime.now());

            // Operator
            dao.save(user);

            // Dependencies

            return "OK";
        } catch (Exception e) {
            return "Save Not Completed:" + e.getMessage();
        }
    }

    // delete mapping for delete user
    @DeleteMapping(value = "/user")
    public String deleteUser(@RequestBody User user) {

        // Authentication and Authorization

        // Excisting

        User extUser = dao.getReferenceById(user.getId());
        if (extUser == null) {
            return "User Doesn't Excists...!";
        }

        try {
            // hard delete
            // dao.delete(extUser);

            // soft delete
            extUser.setStatus(false);
            extUser.setNote("User Account is Removed");
            dao.save(extUser);

            // operation

            return "OK";

        } catch (Exception e) {
            return "Delete not Completed : " + e.getMessage();
        }

    }

    // put mapping fro update user
    @PutMapping(value = "/user")
    public String updateUser(@RequestBody User user) {
        // authentication and authorization

        // need excisting and duplicate
        User extUser = dao.getReferenceById(user.getId());
        if (extUser == null) {
            return "Update not Completed: User not excist..! Check again";

        }

        User extUserEmail = dao.getByEmail(user.getEmail());
        if (extUserEmail != null && user.getId() != extUserEmail.getId()) {
            return "update not completed : User email already excists..!";

        }

        User extUsername = dao.getByUsername(user.getUsername());
        if (extUsername != null && user.getId() != extUsername.getId()) {
            return "update not completed : User already exists..!";

        }

        try {

            // set auto add value

            // password change

            // operation
            dao.save(user);

            // dependencies

            return "OK";
        } catch (Exception e) {
            return "Update not Completed:" + e.getMessage();

        }

    }
}
