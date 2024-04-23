package lk.bitproject.privilege.entity;

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

@Entity
@Table(name = "privilege")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @NotNull
    private Integer id;

    @Column(name = "selpriv")
    @NotNull
    private Boolean selpriv;

    @Column(name = "inspriv")
    @NotNull
    private Boolean inspriv;

    @Column(name = "updpriv")
    @NotNull
    private Boolean updpriv;

    @Column(name = "delpriv")
    @NotNull
    private Boolean delpriv;

    @ManyToOne // user and employee has many to one relationship
    @JoinColumn(name = "role_id", referencedColumnName = "id") // employee_id is a join column
    private Role role_id;

    @ManyToOne // user and employee has many to one relationship
    @JoinColumn(name = "module_id", referencedColumnName = "id") // employee_id is a join column
    private Module module_id;

    public Privilege(Boolean selpriv, Boolean inspriv, Boolean updpriv, Boolean delpriv) {
        this.selpriv = selpriv;
        this.inspriv = inspriv;
        this.updpriv = updpriv;
        this.delpriv = delpriv;

    }

}
