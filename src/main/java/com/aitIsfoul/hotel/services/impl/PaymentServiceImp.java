package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.dao.PaymentDao;
import com.aitIsfoul.hotel.entity.Payment;
import com.aitIsfoul.hotel.entity.dto.request.PaymentConfirmationRequest;
import com.aitIsfoul.hotel.entity.dto.request.PaymentRequest;
import com.aitIsfoul.hotel.entity.dto.response.PaymentConfirmationResponse;
import com.aitIsfoul.hotel.repository.BookingRepository;
import com.aitIsfoul.hotel.repository.PaymentRepository;
import com.aitIsfoul.hotel.services.PaymentService;
import com.aitIsfoul.hotel.utils.StripeHttpClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImp implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final StripeHttpClient stripeHttpClient;
    private final PaymentDao paymentDao;
    @Override
    public String createPaymentIntent(PaymentRequest paymentRequest) {
        Map<String, String> paymentParams=new HashMap<>();
        paymentParams.put("amount", String.valueOf(paymentRequest.getAmount()));
        paymentParams.put("currency",paymentRequest.getCurrency().getName());
        paymentParams.put("payment_method_types[]",paymentRequest.getPayment_method_types());
        try {
            StripeHttpClient.createPaymentIntent(paymentParams);
        } catch (Exception e) {
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }
        return null;
    }
    @Override
    public PaymentConfirmationResponse confirmPayment(PaymentConfirmationRequest confirmationRequest) {
        Payment payment = paymentDao.confirmPayment(confirmationRequest);
        //TODO:I need to add mapper
        return null;
    }
}
