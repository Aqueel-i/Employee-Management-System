package lk.bitproject.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lk.bitproject.user.entity.User;

public interface UserDao extends JpaRepository<User, Integer> {

    // Create Query get user by given Usename
    @Query("select u from User u where u.username=?1")
    public User getByUsername(String username);

    // Create Query get user by given Email
    @Query("select u from User u where u.email=?1")
    public User getByEmail(String email);

    // Create Query for get user by given employee id
    @Query("select u from User u where u.employee_id.id=?1")
    public User getByEmployee(Integer employee_id);

}
