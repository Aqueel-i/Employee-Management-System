package lk.bitproject.employee.cotroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lk.bitproject.employee.dao.EmployeeDao;
import lk.bitproject.privilege.dao.RoleDao;
import lk.bitproject.privilege.entity.Role;
import lk.bitproject.user.dao.UserDao;
import lk.bitproject.user.entity.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;

@RestController

public class LoginController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDao dao;

    @Autowired
    private EmployeeDao daoEmp;

    @Autowired
    private RoleDao daoRole;

    @RequestMapping(value = "/login")
    public ModelAndView loginUI() {
        ModelAndView loginView = new ModelAndView();
        loginView.setViewName("login.html");
        return loginView;
    }

    // define mapping for dashboard ui[/dashboard]
    @RequestMapping(value = "/dashboard")
    public ModelAndView dashboardUI() {

        // get logged User Authentication object using Security context holder
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView dashboardView = new ModelAndView();
        dashboardView.addObject("title", "Dashboard");
        dashboardView.addObject("loggedUser", authentication.getName());
        dashboardView.setViewName("dashboard.html");
        return dashboardView;
    }

    // define mapping for error ui[/error]
    @RequestMapping(value = "/errorpage")
    public ModelAndView errorUI() {
        ModelAndView errorView = new ModelAndView();

        errorView.setViewName("error.html");
        return errorView;
    }

    @GetMapping(value = "/createadmin")
    public String generateAdmin() {

        User extUser = dao.getByUsername("Admin");
        if (extUser == null) {

            User adminUser = new User();

            adminUser.setUsername("Admin");
            adminUser.setPassword(bCryptPasswordEncoder.encode("12345"));
            adminUser.setEmail("admin@gmail.com");
            adminUser.setAdded_datetime(LocalDateTime.now());
            adminUser.setStatus(true);

            adminUser.setEmployee_id(daoEmp.getReferenceById(1));

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(daoRole.getReferenceById(1));

            adminUser.setRoles(userRoles);

            dao.save(adminUser);

        }

        return "<script>window.location.replace('/login');</script>";

    }

}
