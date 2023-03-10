package com.mita.repository;

import com.mita.dto.report.ReportDTO;
import com.mita.dto.report.ReportDetailDTO;
import com.mita.entity.TransactionDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Integer> {
    @Query("""
            SELECT new com.mita.dto.report.ReportDTO(
            CONCAT(th.documentCode,'-',th.documentNumber), th.total, th.date, acc.username)
            FROM TransactionHeader AS th
            LEFT JOIN th.account AS acc
            """)
    List<ReportDTO> getReport(Pageable pagination);

    @Query("""
            SELECT new com.mita.dto.report.ReportDTO(
            CONCAT(th.documentCode,'-',th.documentNumber), th.total, th.date, acc.username)
            FROM TransactionHeader AS th
            LEFT JOIN th.account AS acc
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
    long countTransaction(@Param("username") String username);

    @Query("""
            SELECT new com.mita.dto.report.ReportDetailDTO(
            CONCAT(th.documentCode,'-', th.documentNumber), pro.productName, td.quantity, td.subTotal, acc.username )
            FROM TransactionHeader AS th
            INNER JOIN th.transactionDetails AS td
             INNER JOIN th.account AS acc
            INNER JOIN td.products AS pro
            WHERE CONCAT(th.documentCode,'-', th.documentNumber) = :id
            """)
    List<ReportDetailDTO> getReportDetail(@Param("id") String id, Pageable pagination);

    @Query("""
            SELECT COUNT(*)
            FROM TransactionHeader AS th
            INNER JOIN th.transactionDetails AS td
            INNER JOIN td.products AS pro
            WHERE CONCAT(th.documentCode,'-', th.documentNumber) = :id
            """)
    long countDetail(@Param("id") String id);

    @Query("""
            SELECT new com.mita.dto.report.ReportDetailDTO(
            CONCAT(th.documentCode,'-', th.documentNumber), pro.productName, td.quantity, td.subTotal, acc.username )
            FROM TransactionHeader AS th
            INNER JOIN th.transactionDetails AS td
            INNER JOIN th.account AS acc
            INNER JOIN td.products AS pro
            WHERE CONCAT(th.documentCode,'-', th.documentNumber) = :id
            AND acc.username = :username
            """)
    List<ReportDetailDTO> getReportDetail(@Param("username") String username,@Param("id") String id, Pageable pagination);

    @Query("""
            SELECT COUNT(*)
            FROM TransactionHeader AS th
            INNER JOIN th.transactionDetails AS td
            INNER JOIN th.account AS acc
            INNER JOIN td.products AS pro
            WHERE CONCAT(th.documentCode,'-', th.documentNumber) = :id
            AND acc.username = :username
            """)
    long countDetail(String username, String id);
}
