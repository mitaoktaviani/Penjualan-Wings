package com.mita.dto.transactions;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class UpsertTransaction {
    private String documentCode;
    private String documentNumber;
    private String username;
    private BigDecimal productCode;

}
