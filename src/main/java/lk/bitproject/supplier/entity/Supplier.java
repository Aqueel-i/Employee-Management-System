package lk.bitproject.supplier.entity;

import java.time.LocalDate;

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
import lk.bitproject.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Supplier {

    @Id // PK

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "regno", length = 10)
    @NotNull
    private String regno;

    @Column(name = "suppliername", length = 145)
    @NotNull
    private String suppliername;

    @Column(name = "addeduser_id")
    private Integer addeduser_id;

    @Column(name = "addeddatetime")
    @NotNull
    private LocalDateTime addeddatetime;

    @Column(name = "updateuser_id")
    private String updateuser_id;

    @Column(name = "deleteuser_id")
    private String deleteuser_id;

    @Column(name = "updatedatetime")
    @NotNull
    private LocalDate updatedatetime;

    @Column (name = "deleteddatetime")
    @NotNull
    private LocalDate datetime;

    @ManyToOne
    @JoinColumn(name = "supplierstatus_id", referencedColumnName = "id")
    private SupplierStatus supplierstatus_id;

    @ManyToMany
    @JoinTable(name = "supplier_has_item" , joinColumns = @JoinColumn (name = "supplier_id"),inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> supplyItems;

}
