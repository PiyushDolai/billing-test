package com.dolaicorp.billing.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dolaicorp.billing.app.model.Billing;
import com.dolaicorp.billing.app.repository.BillingRepository;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final BillingRepository billingRepository;

    public ReportController(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @GetMapping
    public String viewReports(Model model) {
        List<Billing> allBillings = billingRepository.findAll();
        model.addAttribute("billings", allBillings);
        return "reports";
    }
}