package com.aitIsfoul.hotel.dao;

import com.aitIsfoul.hotel.entity.Booking;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingDao {
    Booking getBookingById(String id);
    boolean isRoomAvailable(String roomId, LocalDate checkIn, LocalDate checkOut);
    Booking save(Booking booking);
    Booking findByBookingReference(String reference);


}
