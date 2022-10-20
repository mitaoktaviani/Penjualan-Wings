package com.mita.dto.report;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Data
public class  ReportDTO {
    private String id;
    private BigDecimal total;
    private LocalDate date;
    private String username;
    private String product;
    private Long quantity;


}
