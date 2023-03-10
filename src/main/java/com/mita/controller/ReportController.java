package com.mita.controller;

import com.mita.dto.report.ReportDTO;
import com.mita.dto.report.ReportDetailDTO;
import com.mita.entity.TransactionDetail;
import com.mita.entity.TransactionHeader;
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
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private TransactionHeaderService transactionHeaderService;

    @Autowired
    private TransactionDetailService transactionDetailService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1")Integer page,
            Model model){

        List<ReportDTO> grid = transactionDetailService.getAll(page);
        for(ReportDTO value: grid){
        }

        long totalPages = transactionDetailService.getTotalPages();

        model.addAttribute("breadCrumbs","Report");
        model.addAttribute("report",grid);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage", page);

        return "report/report-list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true)String id,
                         @RequestParam(defaultValue = "1")Integer page,
                         Model model){


        List<ReportDetailDTO> reportDetails = transactionDetailService.getReportDetail(id,page);

        long totalPages = transactionDetailService.getTotalPage(id);


        for (ReportDetailDTO rp:reportDetails) {

        }


        model.addAttribute("detail", reportDetails);
        model.addAttribute("breadCrumbs",id + " Detail");
        model.addAttribute("id",id);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage", page);
        return "report/report-detail";
    }





}
