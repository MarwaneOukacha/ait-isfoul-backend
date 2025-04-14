package com.aitIsfoul.hotel.entity.dto.response;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AddUserResponse {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private UserType type;
    private LocalDateTime created;
    private UserStatus status;
}