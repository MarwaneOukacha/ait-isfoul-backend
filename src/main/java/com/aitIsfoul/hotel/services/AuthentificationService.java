package com.aitIsfoul.hotel.services;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface AuthentificationService {
    Map<String, String> generateToken(String email);
    Map<String, String> refreshToken(@RequestParam String refreshToken);
}
