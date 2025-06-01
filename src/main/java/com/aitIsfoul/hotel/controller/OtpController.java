package com.aitIsfoul.hotel.controller;


import com.aitIsfoul.hotel.entity.OtpToken;
import com.aitIsfoul.hotel.entity.dto.request.OtpVerifyRequest;
import com.aitIsfoul.hotel.repository.OtpTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class OtpController {
    @Autowired
    private OtpTokenRepository otpTokenRepository;

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyRequest request) {
      OtpToken otpToken = otpTokenRepository.findByCustomerEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP not found"));
        if (otpToken.isVerified()) {
            return ResponseEntity.badRequest().body("OTP already verified");
        }

        if (!otpToken.getOtpCode().equals(request.getOtp())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }

        if (otpToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.GONE).body("OTP expired");
        }

        otpToken.setVerified(true);
        otpTokenRepository.save(otpToken);

        return ResponseEntity.ok("OTP verified");
    }

}
