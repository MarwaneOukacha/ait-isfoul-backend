package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.AuthentificationDTO;
import com.aitIsfoul.hotel.entity.dto.ChangePasswordDTO;
import com.aitIsfoul.hotel.services.AuthentificationService;
import com.aitIsfoul.hotel.services.CustomerService;
import com.aitIsfoul.hotel.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthentificationController {

    private final AuthentificationService service;
    private final UserService userService;
    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private Authentication authenticate;

    @PostMapping("/login")
    public Map<String, String> Authentification(@RequestBody AuthentificationDTO a) {
        log.info("Login attempt for user: {}", a.getEmail());
        Optional<User> optionalUser = userService.getUserByEmail(a.getEmail());

        if (optionalUser.isEmpty()) {
            log.warn("Authentication failed - user not found: {}", a.getEmail());
            throw new RuntimeException("Incorrect email or password");
        }

        User user = optionalUser.get();

        if (user.isAccountLocked()) {
            if (Duration.between(user.getLockTime(), LocalDateTime.now()).toMinutes() < 15) {
                log.warn("Account is locked for user: {}", a.getEmail());
                throw new RuntimeException("Account is locked. Try again later.");
            } else {
                // Unlock account after timeout
                user.setAccountLocked(false);
                user.setFailedLoginAttempts(0);
            }
        }

        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(a.getEmail(), a.getPassword())
            );

            if (authenticate.isAuthenticated()) {


                // Reset failed attempts
                user.setFailedLoginAttempts(0);
                userService.save(user);
                if(user.getIsActive().equals("Y")){
                    log.info("User {} authenticated successfully", a.getEmail());
                    return service.generateToken(a.getEmail());
                }else{
                    log.error("User {} not activated", a.getEmail());
                    throw new RuntimeException("Account not activated");
                }

            } else {
                throw new RuntimeException("Incorrect password or email");
            }

        } catch (BadCredentialsException e) {
            int attempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(attempts);
            log.warn("Failed login attempt {} for user {}", attempts, a.getEmail());

            if (attempts >= 5) {
                user.setAccountLocked(true);
                user.setLockTime(LocalDateTime.now());
                log.warn("User {} account locked due to too many failed attempts", a.getEmail());
            }

            userService.save(user);
            throw new RuntimeException("Incorrect password or email");
        }
    }


    @PostMapping("/customer/login")
    public Map<String, String> AuthentificationCustomer(@RequestBody AuthentificationDTO a) {
        log.info("Customer login attempt for user: {}", a.getEmail());
        Customer customer = customerService.getCustomerByEmail(a.getEmail());

        if (customer==null) {
            log.warn("Authentication failed - customer not found: {}", a.getEmail());
            throw new RuntimeException("Incorrect email or password");
        }



        if (customer.isAccountLocked()) {
            if (Duration.between(customer.getLockTime(), LocalDateTime.now()).toMinutes() < 15) {
                log.warn("Customer account is locked: {}", a.getEmail());
                throw new RuntimeException("Account is locked. Try again later.");
            } else {
                // Unlock after timeout
                customer.setAccountLocked(false);
                customer.setFailedLoginAttempts(0);
            }
        }

        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(a.getEmail(), a.getPassword())
            );

            if (authenticate.isAuthenticated()) {
                // Reset failed attempts
                customer.setFailedLoginAttempts(0);
                customerService.save(customer);

                if ("Y".equalsIgnoreCase(customer.getIsActive())) {
                    log.info("Customer {} authenticated successfully", a.getEmail());
                    return service.generateToken(a.getEmail());
                } else {
                    throw new RuntimeException("Account not activated");
                }
            } else {
                throw new RuntimeException("Incorrect password or email");
            }

        } catch (BadCredentialsException e) {
            int attempts = customer.getFailedLoginAttempts() + 1;
            customer.setFailedLoginAttempts(attempts);
            log.warn("Failed login attempt {} for customer {}", attempts, a.getEmail());

            if (attempts >= 5) {
                customer.setAccountLocked(true);
                customer.setLockTime(LocalDateTime.now());
                log.warn("Customer {} account locked due to too many failed attempts", a.getEmail());
            }

            customerService.save(customer);
            throw new RuntimeException("Incorrect password or email");
        } catch (Exception e) {
            log.error("Customer authentication error for {}: {}", a.getEmail(), e.getMessage());
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
