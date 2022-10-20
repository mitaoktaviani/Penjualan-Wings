package com.mita.dto.product;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class UpsertProductDTO {
    @Size(max = 18,message = "Product code max 18 characters")
    private String productCode;

    @NotBlank(message = "Product Name is required")
    @Size(max=30, message = "Product name max 30 characters")
    private String productName;
    private BigDecimal price;
    @Size(max = 5, message = "Currency max 5 characters")
    private String currency;
    private Integer discount;
    private String dimension;
    @Size(max = 5, message = "Product unit max 5 characters")
    private String unit;
    private String imagePath;
}
