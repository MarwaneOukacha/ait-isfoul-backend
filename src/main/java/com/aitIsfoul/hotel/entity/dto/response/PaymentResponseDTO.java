package com.aitIsfoul.hotel.entity.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PaymentResponseDTO {
    private String paymentUrl;
}
