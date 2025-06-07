package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.entity.dto.LocationDTO;
import com.aitIsfoul.hotel.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class UpdateHotelResponseDTO {
    private String id;
    private String name;
    private LocationDTO location;
    private int stars;
    private User owner;
    private LocalDateTime updated;
}
