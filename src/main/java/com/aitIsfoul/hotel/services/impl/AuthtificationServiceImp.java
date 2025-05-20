package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.config.SecurityConfig;
import com.aitIsfoul.hotel.dao.CustomerDao;
import com.aitIsfoul.hotel.dao.UserDao;
import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.ChangePasswordDTO;
import com.aitIsfoul.hotel.services.AuthentificationService;
import com.aitIsfoul.hotel.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthtificationServiceImp implements AuthentificationService {

    private final JwtService jwtService;
    private final UserDao userDao;
    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> generateToken(String email) {
        log.info("AuthtificationServiceImp :: Generating token for email: {}", email);
        Map<String, String> tokenMap = jwtService.generateToken(email);
        log.debug("Token generated: {}", tokenMap);
        return tokenMap;
    }

    @Override
    public Map<String, String> refreshToken(@RequestParam String refreshToken) {
        log.info("AuthtificationServiceImp :: Refreshing access token");
        try {
            String newAccessToken = jwtService.refreshAccessToken(refreshToken);
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            log.info("Access token refreshed successfully");
            return response;
        } catch (Exception e) {
            log.error("Failed to refresh token: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void changePassword(ChangePasswordDTO request) {
        String currentUsername = SecurityConfig.getCurrentUsername();
        log.info("Password change attempt for user/customer: {}", currentUsername);

        log.debug("Looking up user with email: {}", currentUsername);
        Optional<User> user = userDao.getUserByEmail(currentUsername);
        if (user.isPresent()) {
            log.debug("User found: {}", currentUsername);

            if (!passwordEncoder.matches(request.getOldPassword(), user.get().getPassword())) {
                log.warn("Incorrect old password for user: {}", currentUsername);
                throw new BadCredentialsException("Old password is incorrect");
            }

            user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
            userDao.save(user.get());
            log.info("Password updated successfully for user: {}", currentUsername);
            return;
        }else{
            log.debug("User not found, looking up customer with email: {}", currentUsername);
            Customer customer = customerDao.findByEmail(currentUsername);
            log.debug("Customer found: {}", currentUsername);

            if (!passwordEncoder.matches(request.getOldPassword(), customer.getPassword())) {
                log.warn("Incorrect old password for customer: {}", currentUsername);
                throw new BadCredentialsException("Old password is incorrect");
            }

            customer.setPassword(passwordEncoder.encode(request.getNewPassword()));
            customerDao.save(customer);
            log.info("Password updated successfully for customer: {}", currentUsername);
            return;
        }
    }

}
