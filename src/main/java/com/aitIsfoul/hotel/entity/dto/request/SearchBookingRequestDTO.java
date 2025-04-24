package com.aitIsfoul.hotel.entity.dto.request;

import com.aitIsfoul.hotel.enums.BookingStatus;
import lombok.Data;

@Data
public class SearchBookingRequestDTO {
    private String bookingReference;
    private String checkIn;
    private String checkOut;
    private BookingStatus status;
    private String clientName; // Optional filter
}

