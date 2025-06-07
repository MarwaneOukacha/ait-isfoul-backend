package com.aitIsfoul.hotel.entity.dto.request;

import com.aitIsfoul.hotel.enums.UserStatus;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class UpdateUserRequest {
    private String id;
    private String iden;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Email(message = "Email must be valid")
    private String email;
    private String isActive;
    private UserStatus status;
}
