package com.aitIsfoul.hotel.services;

import java.util.HashMap;
import java.util.Map;

public interface JwtService {
    Map<String, String> generateToken(String email);
    String createToken(Map<String, Object> claims, String subject, int minutesDuration);
    void validateToken(final String token);

    String refreshAccessToken(String refreshToken);

}