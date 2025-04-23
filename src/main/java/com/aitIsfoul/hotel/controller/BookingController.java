package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.request.BookingRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.BookingResponseDTO;
import com.aitIsfoul.hotel.services.BookingService;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@Slf4j
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("create")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        try {
            BookingResponseDTO response = bookingService.createBooking(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error creating booking: {}", e.getMessage(), e);  // Log the exception
            throw new RuntimeException(e.getMessage());
        }
    }
}
