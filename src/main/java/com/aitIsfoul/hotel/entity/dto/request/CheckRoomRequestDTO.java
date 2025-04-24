package com.aitIsfoul.hotel.entity.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CheckRoomRequestDTO {
    private String roomId;
    private String checkIn;
    private String checkOut;
}
