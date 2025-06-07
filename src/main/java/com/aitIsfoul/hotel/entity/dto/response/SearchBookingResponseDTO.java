package com.aitIsfoul.hotel.entity.dto.response;

import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchBookingResponseDTO {
    private String bookingReference;
    private SearchRoomResponseDTO room;
    private SearchCustomerResponse customer;
    private String checkIn;
    private String checkOut;
    private BookingStatus status;
    private Date created;
}
