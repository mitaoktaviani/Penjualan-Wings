package com.mita.dto.report;

import lombok.*;

import java.math.BigDecimal;



@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Data
public class ReportDetailDTO {
    private String id;
    private String productName;
    private Long quantity;
    private BigDecimal subTotal;
    private String customer;

}
