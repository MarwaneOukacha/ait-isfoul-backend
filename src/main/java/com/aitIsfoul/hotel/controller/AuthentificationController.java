package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.dto.AuthentificationDTO;
import com.aitIsfoul.hotel.services.AuthentificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthentificationController {
    public final AuthentificationService service;
    private Authentication authenticate;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public Map<String, String> Authentification(@RequestBody AuthentificationDTO a){
        authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(a.getEmail(),a.getPassword()));
        if(authenticate.isAuthenticated()){
            return service.generateToken(a.getEmail());
        }else {
            throw new RuntimeException("Mot de passe ou email incorrect");
        }
    }

    @PostMapping("/customer/login")
    public Map<String, String> AuthentificationCustomer(@RequestBody AuthentificationDTO a){
        authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(a.getEmail(),a.getPassword()));
        if(authenticate.isAuthenticated()){
            return service.generateToken(a.getEmail());
        }else {
            throw new RuntimeException("Mot de passe ou email incorrect");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestParam String refreshToken) {

        return ResponseEntity.ok(service.refreshToken(refreshToken));
    }

}
