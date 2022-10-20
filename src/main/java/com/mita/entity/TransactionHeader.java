package com.mita.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
@Entity
@Table(name = "TransactionHeader")
public class TransactionHeader {
    @Id
    @Column(name = "DocumentNumber")
    private String documentNumber;

    @Column(name = "DocumentCode", length = 10)
    private String documentCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Username")
    private Account account;

    @Column(name = "Total", length = 10)
    private BigDecimal total;

    @Column(name = "Date", length = 10)
    private LocalDate date;

    @OneToMany(mappedBy = "transactionHeader", cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<TransactionDetail> transactionDetails;

    public TransactionHeader(String documentNumber, String documentCode, Account account, BigDecimal total, LocalDate date) {
        this.documentNumber = documentNumber;
        this.documentCode = documentCode;
        this.account = account;
        this.total = total;
        this.date = date;
    }

    public void addTransactionDetail(TransactionDetail transactionDetail){
        if(this.transactionDetails == null){
            this.transactionDetails = new LinkedList<>();
        }
        this.transactionDetails.add(transactionDetail);
    }



}
