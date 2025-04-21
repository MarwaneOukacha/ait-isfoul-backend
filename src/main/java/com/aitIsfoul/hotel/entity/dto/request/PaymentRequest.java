package com.aitIsfoul.hotel.entity.dto.request;

import com.aitIsfoul.hotel.entity.Currency;
import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {
    private Long amount;
    private Currency currency;
    private String payment_method_types;
}
