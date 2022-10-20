package com.mita.repository;

import com.mita.entity.TransactionHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionHeaderRepository extends JpaRepository<TransactionHeader, String> {
    @Query("""
            SELECT MAX(th.documentNumber)
            FROM TransactionHeader AS th
            """)
    String getHighestDocNumber();
}
