package com.aitIsfoul.hotel.entity.dto.request;

import com.aitIsfoul.hotel.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchBookingRequestDTO {
    private String keyword;
    private String checkIn;
    private String checkOut;
    private BookingStatus status;
    private String customerEmail;
    private String hotelRef;
}

