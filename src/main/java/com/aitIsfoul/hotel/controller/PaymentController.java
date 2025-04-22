package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.Payment;
import com.aitIsfoul.hotel.entity.dto.request.PaymentConfirmationRequest;
import com.aitIsfoul.hotel.entity.dto.request.PaymentRequest;
import com.aitIsfoul.hotel.entity.dto.response.PaymentConfirmationResponse;
import com.aitIsfoul.hotel.entity.dto.response.PaymentResponseDTO;
import com.aitIsfoul.hotel.services.PaymentService;
import com.stripe.exception.StripeException;
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
    public ResponseEntity<PaymentResponseDTO> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) throws StripeException {

        PaymentResponseDTO payment = paymentService.createPayment(paymentRequest);
        return ResponseEntity.ok(payment);
    }


}
