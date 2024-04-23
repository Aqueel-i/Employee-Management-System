package lk.bitproject.item.entity;

import java.text.DecimalFormat;
import java.time.LocalDate;

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
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "itemcode", unique = true, length = 10)
    @NotNull
    private String itemcode;

    @Column(name = "itemname")
    @NotNull
    private String itemname;

    @Column(name = "purchaseprice")
    @NotNull
    private String purchaseprice;

    @Column(name = "salesprice")
    @NotNull
    private DecimalFormat salesprice;

    @Column(name = "note")
    private String note;

    @Column(name = "rop")
    @NotNull
    private String rop;

    @Column(name = "itemsize")
    @NotNull
    private String itemsize;

    @Column(name = "addeddatetime")
    @NotNull
    private LocalDate addeddatetime;

    @ManyToOne

    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand Brand_id;

    @ManyToOne

    @JoinColumn(name = "packagetype_id", referencedColumnName = "id")
    private Brand packagetype_id;

    @ManyToOne

    @JoinColumn(name = "subcategory_id", referencedColumnName = "id")
    private Brand subcategory_id;

    @ManyToOne

    @JoinColumn(name = "unittype_id", referencedColumnName = "id")
    private Brand unittype_id;

    @ManyToOne

    @JoinColumn(name = "itemstatus_id", referencedColumnName = "id")
    private Brand itemstatus_id;

}
