package lk.bitproject.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lk.bitproject.employee.entity.Employee;
import lk.bitproject.privilege.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

import java.time.LocalDateTime;

@Entity // user class convert as an entity
@Table(name = "user") // table mapping

@Data // generate setter and getter and tostring... etc
@NoArgsConstructor // No argument constructor
@AllArgsConstructor // All Argument constructor

public class User {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI

    @Column(name = "id") // db column mapping
    private Integer id;

    @Column(name = "username", unique = true)
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "photopath")
    private String photopath;

    @Column(name = "note")
    private String note;

    @Column(name = "added_datetime")
    @NotNull
    private LocalDateTime added_datetime;

    @Column(name = "status")
    @NotNull
    private Boolean status;

    @ManyToOne // user and employee has many to one relationship
    @JoinColumn(name = "employee_id", referencedColumnName = "id") // employee_id is a join column
    private Employee employee_id;

    @ManyToMany // user and role has many to many relationship
    @JoinTable(name = "user_has_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
