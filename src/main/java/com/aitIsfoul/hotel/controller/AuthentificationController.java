package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.dto.AuthentificationDTO;
import com.aitIsfoul.hotel.services.AuthentificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthentificationController {
    public final AuthentificationService service;
    private Authentication authenticate;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/Login")
    public String Authentification(@RequestBody AuthentificationDTO a){
        authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(a.getEmail(),a.getPassword()));
        if(authenticate.isAuthenticated()){
            return service.generateToken(a.getEmail());
        }else {
            throw new RuntimeException("Mot de passe ou email incorrect");
        }
    }
}
