package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.request.PaymentConfirmationRequest;
import com.aitIsfoul.hotel.entity.dto.request.PaymentRequest;
import com.aitIsfoul.hotel.entity.dto.response.PaymentConfirmationResponse;
import com.aitIsfoul.hotel.entity.dto.response.PaymentResponseDTO;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequest paymentRequest) throws StripeException;

}
