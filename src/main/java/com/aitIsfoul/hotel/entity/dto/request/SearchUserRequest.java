package com.aitIsfoul.hotel.entity.dto.request;

import com.aitIsfoul.hotel.enums.RoleType;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchUserRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private RoleType role;
    private UserType type;
    private String isActive;
    private LocalDateTime createdStart;
    private LocalDateTime createdEnd;
    private LocalDateTime updatedStart;
    private LocalDateTime updatedEnd;
    private int page = 0;
    private int size = 10;
    private String sort = "id,asc";
    private UserStatus status;

}