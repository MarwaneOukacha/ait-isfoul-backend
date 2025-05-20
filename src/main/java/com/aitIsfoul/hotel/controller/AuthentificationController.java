package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.dto.AuthentificationDTO;
import com.aitIsfoul.hotel.entity.dto.ChangePasswordDTO;
import com.aitIsfoul.hotel.services.AuthentificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthentificationController {

    private final AuthentificationService service;
    private final AuthenticationManager authenticationManager;
    private Authentication authenticate;

    @PostMapping("/login")
    public Map<String, String> Authentification(@RequestBody AuthentificationDTO a) {
        log.info("Login attempt for user: {}", a.getEmail());
        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(a.getEmail(), a.getPassword())
            );
            if (authenticate.isAuthenticated()) {
                log.info("User {} authenticated successfully", a.getEmail());
                return service.generateToken(a.getEmail());
            } else {
                log.warn("Authentication failed for user: {}", a.getEmail());
                throw new RuntimeException("Incorrect password or email");
            }
        } catch (Exception e) {
            log.error("Authentication error for user {}: {}", a.getEmail(), e.getMessage());
            throw e;
        }
    }

    @PostMapping("/customer/login")
    public Map<String, String> AuthentificationCustomer(@RequestBody AuthentificationDTO a) {
        log.info("Customer login attempt for user: {}", a.getEmail());
        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(a.getEmail(), a.getPassword())
            );
            if (authenticate.isAuthenticated()) {
                log.info("Customer {} authenticated successfully", a.getEmail());
                return service.generateToken(a.getEmail());
            } else {
                log.warn("Customer authentication failed for user: {}", a.getEmail());
                throw new RuntimeException("Incorrect password or email");
            }
        } catch (Exception e) {
            log.error("Customer authentication error for user {}: {}", a.getEmail(), e.getMessage());
            throw e;
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestParam String refreshToken) {
        log.info("Token refresh request received");
        try {
            Map<String, String> response = service.refreshToken(refreshToken);
            log.info("Token refreshed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Token refresh failed: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO request) {
        log.info("Password change request for current user");
        try {
            service.changePassword(request);
            log.info("Password changed successfully");
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            log.error("Password change failed: {}", e.getMessage());
            throw e;
        }
    }
}
