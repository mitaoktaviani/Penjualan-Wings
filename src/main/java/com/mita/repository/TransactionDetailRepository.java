package com.mita.repository;

import com.mita.dto.report.ReportDTO;
import com.mita.entity.TransactionDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Integer> {
    @Query("""
            SELECT new com.mita.dto.report.ReportDTO(
            CONCAT(th.documentCode,' - ',th.documentNumber), td.subTotal, th.date, acc.username, pro.productName,td.quantity)
            FROM TransactionHeader AS th
            INNER JOIN th.transactionDetails AS td
            INNER JOIN th.account AS acc
            INNER JOIN td.products AS pro
            """)
    List<ReportDTO> getReport(Pageable pagination);

    @Query("""
            SELECT new com.mita.dto.report.ReportDTO(
            CONCAT(th.documentCode,' - ',th.documentNumber), td.subTotal, th.date, acc.username, pro.productName,td.quantity)
            FROM TransactionHeader AS th
            INNER JOIN th.transactionDetails AS td
            INNER JOIN th.account AS acc
            INNER JOIN td.products AS pro
            WHERE acc.username = :username
            """)
    List<ReportDTO> getReport(@Param("username") String username, Pageable pagination);

    @Query("""
            SELECT COUNT(*)
            FROM TransactionHeader AS th
            INNER JOIN th.transactionDetails AS td
            INNER JOIN th.account AS acc
            INNER JOIN td.products AS pro
            WHERE acc.username = :username
            """)
    long countTransaction(String username);
}
