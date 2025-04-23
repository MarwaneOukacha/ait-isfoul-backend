package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.request.BookingRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.BookingResponseDTO;
import com.stripe.exception.StripeException;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO bookingRequest) throws StripeException;

    Booking getBookingById(String bookingId);

    Booking getBookingByRef(String bookingRef);

}
