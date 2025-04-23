package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.enums.BookingMessage;
import com.aitIsfoul.hotel.enums.BookingSubject;
import com.aitIsfoul.hotel.services.BookingService;
import com.aitIsfoul.hotel.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final BookingService bookingService;
    private final EmailService emailService;

    @PostMapping("/{bookingRef}/notify")
    public ResponseEntity<String> sendBookingConfirmationEmail(@PathVariable String bookingRef) {
        log.info("Received request to send booking confirmation for bookingRef: {}", bookingRef);

        try {
            Booking booking = bookingService.getBookingByRef(bookingRef);
            if (booking == null) {
                log.warn("Booking not found for reference: {}", bookingRef);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
            }

            log.info("Booking found. Sending confirmation email to: {}", booking.getClient().getEmail());

            emailService.sendBookingConfirmation(
                    booking.getClient().getEmail(),
                    BookingSubject.BOOKING_CONFIRMATION.getSubject(),
                    BookingMessage.NOTIFICATION_EMAIL_SENT.getMessage(),
                    booking
            );

            log.info("Booking confirmation email sent successfully to: {}", booking.getClient().getEmail());
            return ResponseEntity.ok("Booking confirmation email sent successfully");

        } catch (Exception e) {
            log.error("Failed to send booking email for bookingRef: {} - {}", bookingRef, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send booking email: " + e.getMessage());
        }
    }
}
