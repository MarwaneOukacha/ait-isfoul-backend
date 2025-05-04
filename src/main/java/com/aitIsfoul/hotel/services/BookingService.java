package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.request.BookingRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.SearchBookingRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.BookingResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchBookingResponseDTO;
import com.stripe.exception.StripeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO bookingRequest) throws StripeException;

    Booking getBookingById(String bookingId);

    BookingResponseDTO getBookingResponseDtoByRef(String bookingRef);
    Booking getBookingByRef(String bookingRef);

    GenericPage<SearchBookingResponseDTO> searchBookings(SearchBookingRequestDTO searchBookingRequestDTO,Pageable pageable);
}
