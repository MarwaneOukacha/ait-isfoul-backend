package com.aitIsfoul.hotel.dao;

import com.aitIsfoul.hotel.entity.Payment;
import com.aitIsfoul.hotel.entity.dto.request.PaymentConfirmationRequest;

public interface PaymentDao {
    public Payment confirmPayment(PaymentConfirmationRequest confirmationRequest);
}
