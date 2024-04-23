package lk.bitproject.employee.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lk.bitproject.employee.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {

    // Query -- @Query()
    // 1 native
    // 2 JPA Query -- default

    // create query for get employee by given nic value // ?1 means param 1
    @Query("select e from Employee e where e.nic=?1")
    public Employee getByNic(String nic);

    // create query for get employee by given Email // ?1 means param 1
    @Query("select e from Employee e where e.email=?1")
    public Employee getByEmail(String email);

    // create query for get next employee number
    @Query(value = "SELECT lpad(max(e.empnum)+1,8,'0')  FROM bitproject_test.employee as e;", nativeQuery = true)
    public String getNextEmployeeNumer();

    // create query to get employee list without user account
    @Query("select e from Employee e where e.id not in (select u.employee_id.id from User u)")
    public List<Employee> getListByWithoutUserAccount();

}
