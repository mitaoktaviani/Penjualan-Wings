package com.mita.service;

import com.mita.dto.report.ReportDTO;

import java.util.List;

public interface TransactionDetailService {

    List<ReportDTO> getAll(Integer page);

    long getTotalPages();

    List<ReportDTO> getAll(String username, Integer page);

    long getTotalPages(String username);
}
