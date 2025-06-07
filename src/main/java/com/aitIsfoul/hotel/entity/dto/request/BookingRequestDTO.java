package com.aitIsfoul.hotel.entity.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookingRequestDTO {
    private String roomId;
    private String customerId;
    private String  checkIn;
    private String  checkOut;
    private int adultsCount;
    private int kidsCount;
    private String currency;
    private String firstName;
    private String email;
    private String phoneNumber;
    private Double total;
}
