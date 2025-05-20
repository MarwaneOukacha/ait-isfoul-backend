package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.dto.ChangePasswordDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface AuthentificationService {
    Map<String, String> generateToken(String email);
    Map<String, String> refreshToken(@RequestParam String refreshToken);

    void changePassword(@Valid ChangePasswordDTO request);
}
