package com.aitIsfoul.hotel.dao;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.request.SearchBookingRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.RoomDetailResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchBookingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingDao {
    Booking getBookingById(String id);
    boolean isRoomAvailable(String roomId, LocalDate checkIn, LocalDate checkOut);
    Booking save(Booking booking);
    Booking findByBookingReference(String reference);
    Page<Booking> searchBookings(SearchBookingRequestDTO criteria, Pageable pageable);
}
