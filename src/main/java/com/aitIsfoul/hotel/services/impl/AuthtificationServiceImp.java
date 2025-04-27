package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.services.AuthentificationService;
import com.aitIsfoul.hotel.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthtificationServiceImp implements AuthentificationService {
    private final JwtService jwtService;

    @Override
    public String generateToken(String email) {
        return jwtService.generateToken(email);
    }
}
