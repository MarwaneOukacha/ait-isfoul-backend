package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetailResponseDTO {
    private String bookingReference;
    private SearchRoomResponseDTO room;
    private SearchCustomerResponse customer;
    private String checkIn;
    private String checkOut;
    private BookingStatus status;
    private int adultsCount;
    private int kidsCount;
    private String currency;
    private String firstName;
    private String email;
    private String phoneNumber;
    private Double total;
    private Double totalPrice;
    private Date created;
}