package com.aitIsfoul.hotel.controller;


import com.aitIsfoul.hotel.entity.OtpToken;
import com.aitIsfoul.hotel.entity.dto.request.OtpResendRequest;
import com.aitIsfoul.hotel.entity.dto.request.OtpVerifyRequest;
import com.aitIsfoul.hotel.repository.OtpTokenRepository;
import com.aitIsfoul.hotel.services.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OtpController {

    private final OtpService otpService;
    private final OtpTokenRepository otpTokenRepository;

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyRequest request) {
        log.info("Received OTP verification request for email: {}", request.getEmail());

        OtpToken otpToken = otpTokenRepository.findByCustomerEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("OTP not found for email: {}", request.getEmail());
                    return new RuntimeException("OTP not found");
                });

        if (otpToken.isVerified()) {
            log.info("OTP already verified for email: {}", request.getEmail());
            return ResponseEntity.badRequest().body("OTP already verified");
        }

        if (!otpToken.getOtpCode().equals(request.getOtp())) {
            log.warn("Invalid OTP provided for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }

        if (otpToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("OTP expired for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.GONE).body("OTP expired");
        }

        otpToken.setVerified(true);
        otpTokenRepository.save(otpToken);
        log.info("OTP successfully verified for email: {}", request.getEmail());

        return ResponseEntity.ok("OTP verified");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody OtpResendRequest request) {
        log.info("Received OTP resend request for email: {}", request.getEmail());

        try {
            otpService.resendOtp(request);
            log.info("OTP resent successfully to email: {}", request.getEmail());
            return ResponseEntity.ok("OTP resent successfully");
        } catch (Exception ex) {
            log.error("Failed to resend OTP to email: {}. Error: {}", request.getEmail(), ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to resend OTP. Please try again later.");
        }
    }
}
