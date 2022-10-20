package com.mita.dto.cart;

import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class CartDTO {
    private String productCode;
    private String username;
    private String productName;
    private Long quantity;
    private BigDecimal price;
    private Integer discount;
    private String unit;
    private BigDecimal subTotal;
    private BigDecimal discountPrice;

    public CartDTO(String productCode, String username, String productName, Long quantity, BigDecimal price, Integer discount, String unit) {
        this.productCode = productCode;
        this.username = username;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.unit = unit;
    }
}
