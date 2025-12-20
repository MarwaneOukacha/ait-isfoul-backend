package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.ContactRequest;
import com.aitIsfoul.hotel.entity.dto.response.BookingResponseDTO;
import com.aitIsfoul.hotel.enums.BookingMessage;
import com.aitIsfoul.hotel.enums.BookingSubject;
import com.aitIsfoul.hotel.services.BookingService;
import com.aitIsfoul.hotel.services.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final BookingService bookingService;
    private final EmailService emailService;

    @PostMapping("/{bookingRef}/sendEmail")
    public ResponseEntity<String> sendBookingConfirmationEmail(@PathVariable String bookingRef) {
        log.info("Received request to send booking confirmation for bookingRef: {}", bookingRef);

        try {
            Booking booking = bookingService.getBookingByRef(bookingRef);
            if (booking == null) {
                log.warn("Booking not found for reference: {}", bookingRef);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
            }

            log.info("Booking found. Sending confirmation email to: {}", booking.getEmail());

            emailService.sendBookingConfirmation(
                    BookingSubject.BOOKING_CONFIRMATION.getSubject(),
                    BookingMessage.NOTIFICATION_EMAIL_SENT.getMessage(),
                    booking
            );

            log.info("Booking confirmation email sent successfully to: {}", booking.getEmail());
            return ResponseEntity.ok("Booking confirmation email sent successfully");

        } catch (Exception e) {
            log.error("Failed to send booking email for bookingRef: {} - {}", bookingRef, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send booking email: " + e.getMessage());
        }
    }
    @PostMapping("/contact")
    public ResponseEntity<String> sendContactMessage(@Valid @RequestBody ContactRequest request) {
        log.info("Received contact message from: {}", request);
        try {
            emailService.sendContactEmail(request);
            return ResponseEntity.ok("Contact message sent successfully");
        } catch (Exception e) {
            log.error("Failed to send contact message: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send contact message");
        }
    }
}
