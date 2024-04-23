package lk.bitproject.employee.entity;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;

@Entity // using entity to convert this class into a entity class

@Table(name = "employee") // mapping with employee table

@Data // to generate getters and setters , add to string etc...

@NoArgsConstructor // default constrctor

@AllArgsConstructor // all argumant construct

public class Employee {

    @Id // PK

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", unique = true) // map with id column and set as unique
    private Integer id;

    @Column(name = "empnum", unique = true, length = 8)
    @NotNull // cannot be null
    private String empnum;

    @Column(name = "fullname", length = 225)
    @NotNull
    private String fullname;

    @Column(name = "callingname", length = 150)
    @NotNull
    private String callingname;

    @Column(name = "nic", unique = true, length = 12)
    @Length(max = 12, min = 10, message = "value length betwen 10 -12")
    @NotNull
    private String nic;

    @Column(name = "gender")
    @NotNull
    private String gender;

    @Column(name = "dob")
    @NotNull
    private LocalDate dob;

    @Column(name = "mobile", length = 10)
    @NotNull
    private String mobile;

    @Column(name = "landno", length = 10)
    private String landno;

    @Column(name = "email", length = 10)
    private String email;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(name = "civilstatus", length = 10)
    @NotNull
    private String civilstatus;

    @Column(name = "note", length = 150)
    private String note;

    @ManyToOne // relationship format

    @JoinColumn(name = "designation_id", referencedColumnName = "id") // join colummn details / csrdiality
    private Designation designation_id;

    @ManyToOne

    @JoinColumn(name = "employeestatus_id", referencedColumnName = "id")
    private EmployeeStatus employeestatus_id;

}
