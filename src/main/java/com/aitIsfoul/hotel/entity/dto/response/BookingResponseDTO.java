package com.aitIsfoul.hotel.entity.dto.response;

import lombok.Data;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookingResponseDTO {
    private String bookingReference;
    private String checkoutUrl;
}
