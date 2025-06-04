package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.dto.request.OtpResendRequest;
import com.aitIsfoul.hotel.entity.dto.request.OtpVerifyRequest;

public interface OtpService {
    void resendOtp(OtpResendRequest request);
}
