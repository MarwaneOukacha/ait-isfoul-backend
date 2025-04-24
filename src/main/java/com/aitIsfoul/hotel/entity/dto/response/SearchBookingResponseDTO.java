package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBookingResponseDTO {
    private String bookingReference;
    private String clientName;
    private String checkIn;
    private String checkOut;
    private BookingStatus status;
}
