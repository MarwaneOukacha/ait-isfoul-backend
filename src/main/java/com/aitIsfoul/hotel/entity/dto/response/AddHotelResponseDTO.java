package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.entity.dto.LocationDTO;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.RoomImageDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddHotelResponseDTO {
    private UUID id;
    private String name;
    private LocationDTO location;
    private int stars;
    private User owner;
    private List<RoomImageDTO> images;
}
