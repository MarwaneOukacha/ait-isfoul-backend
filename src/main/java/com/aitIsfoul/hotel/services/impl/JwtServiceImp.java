package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.Permission;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.enums.PermissionType;
import com.aitIsfoul.hotel.repository.CustomerRepository;
import com.aitIsfoul.hotel.repository.PermissionRepository;
import com.aitIsfoul.hotel.repository.UserRepository;
import com.aitIsfoul.hotel.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImp implements JwtService {

    private final UserRepository repo;
    private final PermissionRepository permissionRepository;
    private final CustomerRepository customerRepository;
    @Value("${app.jwt-secret}")
    private String SECRET; // Valid


    @Override
    public Map<String, String> generateToken(String email) {
        log.info("Generating tokens for email: {}", email);
        Map<String, Object> claims = new HashMap<>();

        var userOptional = repo.findByEmail(email);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            var role = user.getRole();
            List<Permission> permissions = permissionRepository.findByRoleId(role.getId());
            List<PermissionType> permissionNames = permissions.stream()
                    .map(Permission::getPermissionName)
                    .collect(Collectors.toList());

            claims.put("email", user.getEmail());
            claims.put("userID", user.getId());
            claims.put("firstName", user.getFirstName());
            claims.put("lastName", user.getLastName());
            claims.put("phoneNumber", user.getPhoneNumber());
            claims.put("userIden", user.getIden());
            claims.put("role", role.getRoleName());
            claims.put("permissions", permissionNames);

        } else {
            // Check in customer repository
            var customerOptional = customerRepository.findByEmail(email);
            if (customerOptional.isEmpty()) {
                log.error("No User or Customer found with email: {}", email);
                throw new RuntimeException("User not found with email: " + email);
            }

            var customer = customerOptional.get();
            claims.put("email", customer.getEmail());
            claims.put("customerID", customer.getId());
            claims.put("FirstName", customer.getFirstName());
            claims.put("phoneNumber", customer.getPhoneNumber());
            claims.put("LastName", customer.getLastName());
            claims.put("role", "CUSTOMER");

        }

        log.debug("Claims for token generation: {}", claims);

        String accessToken = createToken(claims, email, 30); // 30 minutes
        String refreshToken = createToken(new HashMap<>(), email, 60 * 24 * 7); // 7 days

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        log.info("Tokens generated successfully for email: {}", email);
        return tokens;
    }



    @Override
    public String createToken(Map<String, Object> claims, String subject, int minutesDuration) {
        log.debug("Creating token with claims: {} for subject: {}", claims, subject);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000L * 60 * minutesDuration); // Duration in minutes

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    @Override
    public void validateToken(final String token) {
        log.info("Validating token...");
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            log.info("Token is valid.");
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage());
            throw new RuntimeException("Invalid token");
        }
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        log.info("Refreshing access token...");
        try {
            Claims claims = extractClaims(refreshToken);
            String email = claims.getSubject(); // Email is subject

            log.debug("Email extracted from refresh token: {}", email);

            Optional<User> userOptional = repo.findByEmail(email);
            HashMap<String, Object> newClaims = new HashMap<>();

            if (userOptional.isPresent()) {
                var user = userOptional.get();
                List<Permission> permissions = permissionRepository.findByRoleId(user.getRole().getId());
                List<PermissionType> permissionNames = permissions.stream()
                        .map(Permission::getPermissionName)
                        .collect(Collectors.toList());

                newClaims.put("email", user.getEmail());
                newClaims.put("role", user.getRole().getRoleName());
                newClaims.put("permissions", permissionNames);

            } else {
                // Try finding the email in customer repo
                Optional<Customer> customerOptional = customerRepository.findByEmail(email);
                if (customerOptional.isEmpty()) {
                    log.error("User or Customer not found with email: {}", email);
                    throw new RuntimeException("User not found with email: " + email);
                }

                var customer = customerOptional.get();
                newClaims.put("email", customer.getEmail());
                newClaims.put("role", "CUSTOMER");
                claims.put("FirstName", customer.getFirstName());
                claims.put("LastName", customer.getLastName());
            }

            String newAccessToken = createToken(newClaims, email, 30); // 30 minutes
            log.info("Access token refreshed successfully for email: {}", email);

            return newAccessToken;

        } catch (Exception e) {
            log.error("Invalid refresh token: {}", e.getMessage());
            throw new RuntimeException("Invalid refresh token");
        }
    }


    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        log.debug("Retrieving signing key for JWT...");
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    // Method to extract the username (email in your case) from the token
    @Override
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }


    // Method to validate the token
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}

