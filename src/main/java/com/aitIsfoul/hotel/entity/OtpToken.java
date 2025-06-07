package com.aitIsfoul.hotel.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Entity
@ToString
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

