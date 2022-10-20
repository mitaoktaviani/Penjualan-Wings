package com.mita.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
@Entity
@Table(name = "TransactionDetail")
public class TransactionDetail {
    @EmbeddedId
    private TransactionDetailKey id;

    @Column(name = "DocumentCode")
    private String documentCode;

    @ManyToOne
    @MapsId("documentNumber")
    @JoinColumn(name = "DocumentNumber")
    private TransactionHeader transactionHeader;

    @ManyToOne
    @MapsId("productCode")
    @JoinColumn(name = "ProductCode")
    private Product products;

    @Column(name = "Price", length = 6)
    private BigDecimal price;

    @Column(name = "Unit", length = 5)
    private String unit;

    @Column(name = "Quantity", length = 6)
    private Long quantity;

    @Column(name = "SubTotal", length = 10)
    private BigDecimal subTotal;

    public TransactionDetail(String documentCode, TransactionHeader transactionHeader,
                             Product products, BigDecimal price, String unit, Long quantity, BigDecimal subTotal) {
        this.id = new TransactionDetailKey(transactionHeader.getDocumentNumber(),products.getProductCode());
        this.documentCode = documentCode;
        this.transactionHeader = transactionHeader;
        this.products = products;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }
}
