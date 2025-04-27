package com.aitIsfoul.hotel.services;

import java.util.HashMap;

public interface JwtService {
    public String generateToken(String email);
    public String createToken(HashMap<String, Object> claims, String email);
    public void validateToken(final String token);
}