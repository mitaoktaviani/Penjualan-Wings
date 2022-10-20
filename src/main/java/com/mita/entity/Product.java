package com.mita.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @Column(name = "ProductCode")
    private String productCode;

    @Column(name = "ProductName", length = 30)
    private String productName;

    @Column(name = "Price",length = 6)
    private BigDecimal price;

    @Column(name = "Currency", length = 5)
    private String currency;

    @Column(name = "Discount", length = 5)
    private Integer discount;

    @Column(name = "Dimension", length = 50)
    private String dimension;

    @Column(name = "Unit", length = 5)
    private String unit;

    @Column(name="ImagePath")
    private String imagePath;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Cart",
            joinColumns = @JoinColumn(name = "ProductCode"),
            inverseJoinColumns = @JoinColumn(name = "Username")
    )
    private List<Account> accounts;

    @OneToMany(mappedBy = "products")
    private List<TransactionDetail> transactionDetails;

    public Product(String productCode, String productName, BigDecimal price, String currency, Integer discount, String dimension, String unit, String imagePath) {
        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
        this.currency = currency;
        this.discount = discount;
        this.dimension = dimension;
        this.unit = unit;
        this.imagePath = imagePath;
    }


}
