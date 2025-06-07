package com.aitIsfoul.hotel.entity.dto.request;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class CheckRoomRequestDTO {
    private String roomId;
    private String checkIn;
    private String checkOut;
}
