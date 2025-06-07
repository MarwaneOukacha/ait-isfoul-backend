package com.aitIsfoul.hotel.entity.dto.response;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@ToString
public class AddUserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private UserType type;
    private LocalDateTime created;
    private UserStatus status;
    private String iden;
}