package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.repository.UserRepository;
import com.aitIsfoul.hotel.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImp implements JwtService {
    private final UserRepository repo;
    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    @Override
    public String generateToken(String email) {
        HashMap<String, Object> claims = new HashMap<>();
        var username =repo.findByUsername(email).get().getEmail();
        var role=repo.findByUsername(email).get().getRole();
        claims.put("Username",username);
        claims.put("Role",role);
        return createToken(claims,email);
    }

    @Override
    public String createToken(HashMap<String, Object> claims, String email) {
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)).signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
