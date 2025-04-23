package com.aitIsfoul.hotel.entity.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingRequestDTO {
    private String roomId;
    private String clientId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int adultsCount;
    private int kidsCount;
    private String currency;
}
