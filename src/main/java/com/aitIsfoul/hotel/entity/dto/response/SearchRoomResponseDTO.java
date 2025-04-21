package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.enums.RoomStatus;
import com.aitIsfoul.hotel.enums.RoomType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchRoomResponseDTO {
    private String title;
    private RoomType roomType;
    private int maxPeople;
    private Double price;
    private String description;
    private String facilitiesDesc;
    private RoomStatus roomStatus;
    private LocalDateTime created;
    private LocalDateTime updated;

}
