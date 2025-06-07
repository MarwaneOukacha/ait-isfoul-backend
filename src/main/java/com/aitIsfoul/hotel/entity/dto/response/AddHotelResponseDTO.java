package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.entity.dto.LocationDTO;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.RoomImageDTO;
import com.aitIsfoul.hotel.entity.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddHotelResponseDTO {
    private UUID id;
    private String name;
    private LocationDTO location;
    private int stars;
    private UserDto owner;
    private List<RoomImageDTO> images;
    private LocalDateTime created;
    private String hotelIden;
}
