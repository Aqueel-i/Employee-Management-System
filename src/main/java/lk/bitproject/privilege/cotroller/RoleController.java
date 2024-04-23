package lk.bitproject.privilege.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.bitproject.privilege.dao.RoleDao;
import lk.bitproject.privilege.entity.Role;

@RestController
public class RoleController {

    @Autowired // dao variable ekata role dao objrct ekk input karnawa
    private RoleDao dao; // create dao variable , dao variable eke data type eka roledao

    @GetMapping(value = "/role/listwithoutadmin", produces = "application/json")
    public List<Role> getRoleListWithoutAdmin() {
        return dao.getRoleListWithoutAdmin();
    }

}
