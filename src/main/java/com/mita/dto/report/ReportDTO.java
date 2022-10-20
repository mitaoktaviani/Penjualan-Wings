package com.mita.dto.report;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Data
public class ReportDTO {
    private String documentNumber;
    private String documentCode;
    private BigDecimal total;
    private LocalDate date;
}
