package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.services.AuthentificationService;
import com.aitIsfoul.hotel.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthtificationServiceImp implements AuthentificationService {
    private final JwtService jwtService;

    @Override
    public Map<String, String> generateToken(String email) {
        return jwtService.generateToken(email);
    }
    @Override
    public Map<String, String> refreshToken(@RequestParam String refreshToken) {
        String newAccessToken = jwtService.refreshAccessToken(refreshToken);
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        return response;
    }
}
