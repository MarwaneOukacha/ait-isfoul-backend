package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.entity.OtpToken;
import com.aitIsfoul.hotel.entity.dto.request.OtpResendRequest;
import com.aitIsfoul.hotel.entity.dto.request.OtpVerifyRequest;
import com.aitIsfoul.hotel.repository.OtpTokenRepository;
import com.aitIsfoul.hotel.services.EmailService;
import com.aitIsfoul.hotel.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpServiceImp implements OtpService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpTokenRepository otpTokenRepository;

    @Override
    public void resendOtp(OtpResendRequest request) {
        String email = request.getEmail();

        OtpToken otpToken = otpTokenRepository.findByCustomerEmail(email)
                .orElse(new OtpToken());

        String newOtp = generateOtp(); // Generate a new 6-digit OTP
        LocalDateTime newExpiry = LocalDateTime.now().plusMinutes(10);

        otpToken.setOtpCode(newOtp);
        otpToken.setExpiresAt(newExpiry);
        otpToken.setVerified(false);

        otpTokenRepository.save(otpToken);

        emailService.sendOtp(otpToken.getCustomer().getEmail(),newOtp);
    }



    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
