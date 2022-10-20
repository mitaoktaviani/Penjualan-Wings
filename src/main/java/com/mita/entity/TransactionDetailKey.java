package com.mita.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
@Embeddable
public class TransactionDetailKey implements Serializable {
    @Column(name = "DocumentNumber")
    private String documentNumber;

    @Column(name = "ProductCode")
    private String productCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDetailKey that = (TransactionDetailKey) o;
        return Objects.equals(documentNumber, that.documentNumber) && Objects.equals(productCode, that.productCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentNumber, productCode);
    }
}
