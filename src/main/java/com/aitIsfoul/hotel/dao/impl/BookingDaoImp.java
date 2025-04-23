package com.aitIsfoul.hotel.dao.impl;

import com.aitIsfoul.hotel.dao.BookingDao;
import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingDaoImp implements BookingDao {
    private BookingRepository bookingRepository;
    @Override
    public Booking getBookingById(String id) {
        Booking booking = bookingRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        return booking;
    }
    @Override
    public boolean isRoomAvailable(String roomId, LocalDate checkIn, LocalDate checkOut) {
        return bookingRepository.isRoomAvailable(roomId, checkIn, checkOut);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> findByBookingReference(String reference) {
        return bookingRepository.findByBookingReference(reference);
    }
}
