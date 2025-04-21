package com.aitIsfoul.hotel.entity.dto.request;

import com.aitIsfoul.hotel.enums.RoomStatus;
import com.aitIsfoul.hotel.enums.RoomType;
import lombok.Data;



@Data
public class UpdateRoomRequestDTO {
    private String id;
    private String title;
    private RoomType roomType;
    private int maxPeople;
    private Double price;
    private String description;
    private String facilitiesDesc;
    private String isActive;
    private RoomStatus roomStatus;
}
