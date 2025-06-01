package com.aitIsfoul.hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Entity
public class OtpToken {
    @Id
    @GeneratedValue
    private UUID id;

    private String otpCode;

    private LocalDateTime expiresAt;

    private boolean verified = false;

    @OneToOne
    private Customer customer;




}

