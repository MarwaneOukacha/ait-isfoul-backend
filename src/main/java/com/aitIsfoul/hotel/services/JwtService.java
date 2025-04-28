package com.aitIsfoul.hotel.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface JwtService {
    Map<String, String> generateToken(String email);
    String createToken(Map<String, Object> claims, String subject, int minutesDuration);
    void validateToken(final String token);

    String refreshAccessToken(String refreshToken);
    Claims extractClaims(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
    String extractUsername(String token);
}