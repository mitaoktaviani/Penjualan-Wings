package com.mita.service;

import com.mita.dto.report.ReportDTO;
import com.mita.dto.report.ReportDetailDTO;
import com.mita.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {

    List<ReportDTO> getAll(Integer page);

    long getTotalPages();

    List<ReportDTO> getAll(String username, Integer page);

    long getTotalPages(String username);

    List<ReportDetailDTO> getReportDetail(String id, Integer page);

    long getTotalPage(String id);

    List<ReportDetailDTO> getReportDetail(String username, String id, Integer page);

    long getTotalPage(String username, String id);
}
