package com.aitIsfoul.hotel.entity.dto;

import com.aitIsfoul.hotel.enums.RoleType;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private UserType type;
    private String isActive;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String iden;
    private UserStatus status;
}
