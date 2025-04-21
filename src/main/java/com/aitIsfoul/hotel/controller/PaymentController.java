package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.Payment;
import com.aitIsfoul.hotel.entity.dto.request.PaymentConfirmationRequest;
import com.aitIsfoul.hotel.entity.dto.request.PaymentRequest;
import com.aitIsfoul.hotel.entity.dto.response.PaymentConfirmationResponse;
import com.aitIsfoul.hotel.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        String clientSecret = paymentService.createPaymentIntent(paymentRequest);
        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", clientSecret);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Payment> confirmPayment(@RequestBody PaymentConfirmationRequest confirmationRequest) {
        PaymentConfirmationResponse payment = paymentService.confirmPayment(confirmationRequest);
        return null;
    }
}
