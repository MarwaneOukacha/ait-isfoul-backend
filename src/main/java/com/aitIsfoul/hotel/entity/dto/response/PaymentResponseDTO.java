package com.aitIsfoul.hotel.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseDTO {
    private String paymentUrl;
}
