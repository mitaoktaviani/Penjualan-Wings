package com.mita.dto.product;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class ProductGridDTO {
    private String productCode;
    private String productName;
    private BigDecimal price;
    private String currency;
    private BigDecimal discount;
    private String dimension;
    private String unit;
    private String imagePath;
}
