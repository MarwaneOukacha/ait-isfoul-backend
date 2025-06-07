package com.aitIsfoul.hotel.entity.dto.response;
import com.aitIsfoul.hotel.enums.UserStatus;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class UpdateUserResponse {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String isActive;
    private String iden;
    private UserStatus status;
}