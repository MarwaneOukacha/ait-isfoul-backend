package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.dto.request.PaymentConfirmationRequest;
import com.aitIsfoul.hotel.entity.dto.request.PaymentRequest;
import com.aitIsfoul.hotel.entity.dto.response.PaymentConfirmationResponse;

public interface PaymentService {
    String createPaymentIntent(PaymentRequest paymentRequest);

    PaymentConfirmationResponse confirmPayment(PaymentConfirmationRequest confirmationRequest);

}
