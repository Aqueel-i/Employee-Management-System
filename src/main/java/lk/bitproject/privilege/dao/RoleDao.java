package lk.bitproject.privilege.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lk.bitproject.privilege.entity.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {

    // create query for get roles without admiin role
    @Query("select r from Role r where r.name <> 'Admin'")
    java.util.List<Role> getRoleListWithoutAdmin();

}
