package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.request.BookingRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.SearchBookingRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.BookingResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchBookingResponseDTO;
import com.aitIsfoul.hotel.enums.BookingStatus;
import com.aitIsfoul.hotel.enums.UserType;
import com.aitIsfoul.hotel.services.BookingService;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public ResponseEntity<GenericPage<SearchBookingResponseDTO>> search(@RequestParam(required = false) String keyword,
                                                                        @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) String checkIn, @RequestParam(required = false) String checkOut
            , Pageable pageable){
        SearchBookingRequestDTO searchBookingRequestDTO=SearchBookingRequestDTO
                .builder()
                .checkIn(checkIn)
                .checkOut(checkOut)
                .status(status)
                .build();
        return ResponseEntity.ok(bookingService.searchBookings(searchBookingRequestDTO,pageable));
    }

}
