package com.aitIsfoul.hotel.entity.dto.request;

import com.aitIsfoul.hotel.entity.Currency;
import com.aitIsfoul.hotel.enums.PaymentMethode;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PaymentRequest {
    private String bookingId;
    private PaymentMethode paymentMethode;
}
