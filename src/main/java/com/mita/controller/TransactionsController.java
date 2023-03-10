package com.mita.controller;

import com.mita.dto.report.ReportDTO;
import com.mita.dto.report.ReportDetailDTO;
import com.mita.service.TransactionDetailService;
import com.mita.service.TransactionHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private TransactionHeaderService transactionHeaderService;

    @Autowired
    private TransactionDetailService transactionDetailService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1")Integer page,
            Model model){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ReportDTO> grid = transactionDetailService.getAll(username,page);

        long totalPages = transactionDetailService.getTotalPages(username);

        model.addAttribute("breadCrumbs","Transactions");
        model.addAttribute("transactions",grid);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage", page);

        return "transactions/transactions-list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true)String id,
                         @RequestParam(defaultValue = "1")Integer page,
                         Model model){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ReportDetailDTO> reportDetails = transactionDetailService.getReportDetail(username, id,page);

        long totalPages = transactionDetailService.getTotalPage(username,id);


        for (ReportDetailDTO rp:reportDetails) {
            System.out.println(rp);
        }

        model.addAttribute("detail", reportDetails);
        model.addAttribute("breadCrumbs",id + " Detail");
        model.addAttribute("id",id);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage", page);

        return "transactions/transactions-detail";
    }


}
