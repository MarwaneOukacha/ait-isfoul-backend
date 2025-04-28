package com.aitIsfoul.hotel.dao.impl;

import com.aitIsfoul.hotel.dao.BookingDao;
import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.request.SearchBookingRequestDTO;
import com.aitIsfoul.hotel.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingDaoImp implements BookingDao {

    private final BookingRepository bookingRepository;

    @Override
    public Booking getBookingById(String id) {
        log.info("Fetching booking by ID: {}", id);
        try {
            Booking booking = bookingRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
            log.debug("Booking retrieved successfully: {}", booking.getBookingReference());
            return booking;
        } catch (EntityNotFoundException e) {
            log.warn("Booking not found for ID: {}", id);
            throw e;
        }
    }

    @Override
    public boolean isRoomAvailable(String roomId, LocalDate checkIn, LocalDate checkOut) {
        log.info("Checking availability for roomId: {}, from {} to {}", roomId, checkIn, checkOut);
        boolean available = bookingRepository.isRoomAvailable(UUID.fromString(roomId), checkIn, checkOut);
        log.debug("Room availability for roomId {}: {}", roomId, available);
        return available;
    }

    @Override
    public Booking save(Booking booking) {
        log.info("Saving booking for client: {}, reference: {}", booking.getCustomer().getEmail(), booking.getBookingReference());
        Booking saved = bookingRepository.save(booking);
        log.debug("Booking saved with ID: {}", saved.getId());
        return saved;
    }

    @Override
    public Booking findByBookingReference(String reference) {
        log.info("Fetching booking by reference: {}", reference);
        try {
            Booking booking = bookingRepository.findByBookingReference(reference)
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
            log.debug("Booking found: ID = {}", booking.getId());
            return booking;
        } catch (EntityNotFoundException e) {
            log.warn("Booking not found for reference: {}", reference);
            throw e;
        }
    }

    @Override
    public Page<Booking> searchBookings(SearchBookingRequestDTO criteria, Pageable pageable) {
        return bookingRepository.findAllWithCriteria(criteria,pageable);
    }
}

