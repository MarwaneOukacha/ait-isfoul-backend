package com.aitIsfoul.hotel.entity.dto;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserCriteria {
    private String keyword;
    private String roleName;
    private UserStatus status;
    private UserType type;
}