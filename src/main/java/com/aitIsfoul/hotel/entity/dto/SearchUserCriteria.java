package com.aitIsfoul.hotel.entity.dto;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchUserCriteria {
    private String keyword;
    private String roleName;
    private UserStatus status;
    private UserType type;
}