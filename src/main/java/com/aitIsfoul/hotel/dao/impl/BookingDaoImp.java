package com.aitIsfoul.hotel.dao.impl;

import com.aitIsfoul.hotel.dao.BookingDao;
import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
