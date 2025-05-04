package com.dolaicorp.billing.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dolaicorp.billing.app.dto.CartItemDTO;
import com.dolaicorp.billing.app.model.Billing;
import com.dolaicorp.billing.app.model.BillingItem;
import com.dolaicorp.billing.app.repository.BillingRepository;

@Controller
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingRepository billingRepository;

    public BillingController(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @PostMapping("/complete")
    @ResponseBody // Ensure the response is treated as the body
    public ResponseEntity<?> completeBilling(@RequestBody List<CartItemDTO> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return new ResponseEntity<>("Cart is empty", HttpStatus.BAD_REQUEST);
        }

        Billing billing = new Billing();
        List<BillingItem> billingItems = cartItems.stream()
                .map(item -> {
                    BillingItem billingItem = new BillingItem(item.getName(), item.getPrice(), item.getQuantity(), item.getPrice() * item.getQuantity());
                    billingItem.setBilling(billing);
                    return billingItem;
                })
                .collect(Collectors.toList());

        billing.setBillingItems(billingItems);
        billing.setTotalAmount(billingItems.stream().mapToDouble(BillingItem::getItemTotal).sum());

        Billing savedBilling = billingRepository.save(billing);

        // Return the ID of the saved billing instead of the entire entity
        return new ResponseEntity<>(savedBilling.getId(), HttpStatus.OK);
        // Or, return a simple success message:
        // return new ResponseEntity<>("Billing completed successfully with ID: " + savedBilling.getId(), HttpStatus.OK);
    }
}