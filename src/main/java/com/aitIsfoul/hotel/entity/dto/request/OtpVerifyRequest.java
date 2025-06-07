package com.aitIsfoul.hotel.entity.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OtpVerifyRequest {
    private String email;
    private String otp;
}
