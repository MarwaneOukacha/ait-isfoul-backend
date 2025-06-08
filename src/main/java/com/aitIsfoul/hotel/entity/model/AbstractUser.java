package com.aitIsfoul.hotel.entity.model;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractUser extends AbstractEntity{
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String iden;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Enumerated(EnumType.STRING)
    private UserType type;
    @Column()
    private String password;
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer failedLoginAttempts = 0;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean accountLocked = false;
    @Column()
    private LocalDateTime lockTime;

    public Boolean isAccountLocked(){
        return accountLocked;
    }
}
