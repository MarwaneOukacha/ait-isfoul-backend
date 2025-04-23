package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.dao.BookingDao;
import com.aitIsfoul.hotel.dao.CustomerDao;
import com.aitIsfoul.hotel.dao.RoomDao;
import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.request.BookingRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.BookingResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.PaymentResponseDTO;
import com.aitIsfoul.hotel.enums.BookingStatus;
import com.aitIsfoul.hotel.mapper.BookingMapper;
import com.aitIsfoul.hotel.services.BookingService;
import com.aitIsfoul.hotel.services.PaymentService;
import com.aitIsfoul.hotel.utils.Utils;
import com.stripe.exception.StripeException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {
    private final RoomDao roomDao;
    private final BookingDao bookingDao;
    private final CustomerDao customerDao;
    private final PaymentService paymentService;
    private final BookingMapper bookingMapper;
    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequest) throws StripeException {

        Room room = roomDao.findById(bookingRequest.getRoomId());
        LocalDate checkInDate = LocalDate.parse(bookingRequest.getCheckIn());
        LocalDate checkOutDate = LocalDate.parse(bookingRequest.getCheckOut());

        boolean available = bookingDao.isRoomAvailable(bookingRequest.getRoomId(), checkInDate, checkOutDate);
        if (!available) {
            throw new IllegalStateException("Room not available");
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setClient(customerDao.findById(bookingRequest.getClientId()));
        booking.setCheckIn(checkInDate);
        booking.setCheckOut(checkOutDate);
        booking.setAdultsCount(bookingRequest.getAdultsCount());
        booking.setKidsCount(bookingRequest.getKidsCount());
        booking.setCurrency(bookingRequest.getCurrency());
        booking.setStatus(BookingStatus.PENDING_PAYMENT);
        booking.setBookingReference(Utils.generateBookingCode());

        booking = bookingDao.save(booking);

        PaymentResponseDTO paymentResponse = paymentService.createPayment(booking);

        BookingResponseDTO response = bookingMapper.toBookingResponseDTO(booking);
        response.setCheckoutUrl(paymentResponse.getPaymentUrl());

        return response;
    }

}
