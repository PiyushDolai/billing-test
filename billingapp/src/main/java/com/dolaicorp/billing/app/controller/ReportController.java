package com.dolaicorp.billing.app.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dolaicorp.billing.app.model.Billing;
import com.dolaicorp.billing.app.repository.BillingRepository;

@Controller
public class ReportController {

    private final BillingRepository billingRepository;

    public ReportController(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @GetMapping("/reports")
    public String viewReports(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Billing> billingPage;

        if (startDateStr != null && !startDateStr.isEmpty() && endDateStr != null && !endDateStr.isEmpty()) {
            try {
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);
                LocalDateTime startDateTime = startDate.atStartOfDay();
                LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
                billingPage = billingRepository.findByBillingDateBetween(startDateTime, endDateTime, pageable);
                model.addAttribute("startDate", startDateStr);
                model.addAttribute("endDate", endDateStr);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Invalid date format. Please use YYYY-MM-DD.");
                billingPage = billingRepository.findAllByOrderByBillingDateDesc(pageable);
            }
        } else {
            billingPage = billingRepository.findAllByOrderByBillingDateDesc(pageable);
        }

        model.addAttribute("billings", billingPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", billingPage.getTotalPages());
        model.addAttribute("totalItems", billingPage.getTotalElements());
        model.addAttribute("pageSize", size);

        return "reports";
    }
}