package lk.bitproject.privilege.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lk.bitproject.privilege.dao.PrivilegeDao;
import lk.bitproject.privilege.entity.Privilege;

import java.util.List;

// Restcontroller is used for call the UI and handle the mapping

@RestController

public class PrivilegeController {

    @Autowired // Dependency Injection....inject employee dao instance into dao variable

    private PrivilegeDao dao;

    // Request Mapping for get privilege UI [URL --> /privilege]
    @RequestMapping(value = "/privilege")
    public ModelAndView privilegeUI() {

        // get logged User Authentication object using Security context holder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView privilegeView = new ModelAndView();
        privilegeView.addObject("title", "Privilege Management");
        privilegeView.addObject("loggedUser", authentication.getName());
        privilegeView.setViewName("privilege.html");

        return privilegeView;

    }

    // Get Mapping for get all data [URL /privilege/alldata]
    @GetMapping(value = "/privilege/alldata", produces = "application/json")
    public List<Privilege> getAllData() {
        return dao.findAll();
    }

    // delete mapping for delete Privilege
    @DeleteMapping(value = "/privilege")
    public String deletePrivilege(@RequestBody Privilege privilege) {

        // Authentication and Authorization

        // Excisting

        Privilege extPrivilege = dao.getReferenceById(privilege.getId());
        if (extPrivilege == null) {
            return "Privilege Doesn't Exists...!";
        }

        try {
            // hard delete
            // dao.delete(extPrivilege);

            // soft delete
            extPrivilege.setSelpriv(false);
            extPrivilege.setInspriv(false);
            extPrivilege.setUpdpriv(false);
            extPrivilege.setDelpriv(false);
            dao.save(extPrivilege);

            // operation

            return "OK";

        } catch (Exception e) {
            return "Delete not Completed : " + e.getMessage();
        }

    }

    /// privilege/bymodule /{Employee}
    @GetMapping(value = "/privilege/bymodule/{modulename}", produces = "application/json") // path varible
    // @GetMapping(value = "/privilege/bymodule", params = "{modulename}", produces
    // = "application/json") // query variable
    public Privilege getPrivilegeByUserModule(@PathVariable("modulename") String modulename) {
        // return dao.getPrivilegeByUserModule();

        // get logged user authentication object using security context holder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getPrivilegeByUserModule(authentication.getName(), modulename);
    }

    public Privilege getPrivilegeByUserModule(String username, String modulename) {

        if (username.equals("Admin")) {
            return new Privilege(true, true, false, false);

        } else {
            String privi = dao.getPrivilegeByUserModule(username, modulename); // 1,1,1,1 --> [1,1,1,1]
            String[] priviArray = privi.split(",");

            Boolean select = priviArray[0].equals("1");
            Boolean insert = priviArray[1].equals("1");
            Boolean update = priviArray[2].equals("1");
            Boolean delete = priviArray[3].equals("1");

            return new Privilege(select, insert, update, delete);
        }
    }

}
