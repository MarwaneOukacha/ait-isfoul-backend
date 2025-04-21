package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.enums.RoomStatus;
import com.aitIsfoul.hotel.enums.RoomType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class AddRoomResponseDTO {
    private UUID id;
    private String title;
    private RoomType roomType;
    private int maxPeople;
    private Double price;
    private String description;
    private String facilitiesDesc;
    private RoomStatus roomStatus;
    private LocalDateTime created;
    private User createdBy;
}
