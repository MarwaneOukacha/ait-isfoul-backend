package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.enums.RoleType;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private RoleType role;  // Assuming you want to filter by role type
    private UserType type;  // Assuming you want to filter by user type
    private String isActive;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String iden;
    private UserStatus status;
}