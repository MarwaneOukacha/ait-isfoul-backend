package com.aitIsfoul.hotel.entity.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthentificationDTO {
    private String email;
    private String password;
}
