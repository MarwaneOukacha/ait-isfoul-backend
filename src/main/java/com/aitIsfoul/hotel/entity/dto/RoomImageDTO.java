package com.aitIsfoul.hotel.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomImageDTO {
    private String name;
    private String type;
    private String data; // base64 string
}
