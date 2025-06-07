package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.enums.RoomStatus;
import com.aitIsfoul.hotel.enums.RoomType;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@ToString
public class UpdateRoomResponseDTO {
    private UUID id;
    private String title;
    private RoomType roomType;
    private int maxPeople;
    private Double price;
    private String description;
    private String facilitiesDesc;
    private String isActive;
    private LocalDateTime updated;
    private User updatedBy;
    private RoomStatus roomStatus;
}
