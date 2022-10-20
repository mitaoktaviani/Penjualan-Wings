package com.mita.service;

import com.mita.dto.report.ReportDTO;
import com.mita.repository.TransactionDetailRepository;
import com.mita.repository.TransactionHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService{

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Autowired
    private TransactionHeaderRepository transactionHeaderRepository;

    private final int rowsInPage = 5;

    @Override
    public List<ReportDTO> getAll(Integer page) {

        Pageable pagination = PageRequest.of(page-1, rowsInPage, Sort.by("id"));

        List<ReportDTO> reportDTOS = transactionDetailRepository.getReport(pagination);

        return reportDTOS;
    }

    @Override
    public long getTotalPages() {
        double totalData = (double)(transactionDetailRepository.count());
        long totalPage = (long)(Math.ceil(totalData / rowsInPage));
        return totalPage;
    }

    @Override
    public List<ReportDTO> getAll(String username, Integer page) {
        Pageable pagination = PageRequest.of(page-1, rowsInPage, Sort.by("id"));

        List<ReportDTO> reportDTOS = transactionDetailRepository.getReport(username,pagination);

        return reportDTOS;
    }

    @Override
    public long getTotalPages(String username) {
        double totalData = (double)(transactionDetailRepository.countTransaction(username));
        long totalPage = (long)(Math.ceil(totalData / rowsInPage));
        return totalPage;
    }
}
