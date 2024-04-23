package lk.bitproject.privilege.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lk.bitproject.privilege.entity.Privilege;

public interface PrivilegeDao extends JpaRepository<Privilege, Integer> {

    // this is a native query which is directly got from workbench and this includes
    // table names and column names.. Nother query we can use is Jpa queries. It
    // uses classes intead of tables and columns
    @Query(value = "SELECT bit_or(p.selpriv) as sel,bit_or(p.inspriv) as inst,bit_or(p.updpriv) as upd,bit_or(p.delpriv) as del  FROM bitproject_test.privilege as p where p.module_id in (select m.id from bitproject_test.module as m where m.name=?2)and p.role_id in (select uhr.role_id from bitproject_test.user_has_role as uhr where uhr.user_id in (select u.id from bitproject_test.user as u where u.username=?1)) ; ", nativeQuery = true)

    public String getPrivilegeByUserModule(String username, String modulename);
}
