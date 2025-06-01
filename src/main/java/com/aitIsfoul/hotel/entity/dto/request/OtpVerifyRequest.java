package com.aitIsfoul.hotel.entity.dto.request;

import lombok.Data;

@Data
public class OtpVerifyRequest {
    private String email;
    private String otp;
}
