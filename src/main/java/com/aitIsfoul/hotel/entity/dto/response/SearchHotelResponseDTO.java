package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.RoomImageDTO;
import com.aitIsfoul.hotel.entity.model.Location;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class SearchHotelResponseDTO {
    private String name;
    private Location location;
    private int stars;
    private User owner;
    private List<RoomImageDTO> images;
    private String hotelIden;
    private LocalDateTime created;
    private LocalDateTime updated;
}
