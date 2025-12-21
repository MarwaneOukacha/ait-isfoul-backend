package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.dao.BookingDao;
import com.aitIsfoul.hotel.dao.CustomerDao;
import com.aitIsfoul.hotel.dao.RoomDao;
import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.request.BookingRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.SearchBookingRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.BookingDetailResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.BookingResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.PaymentResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchBookingResponseDTO;
import com.aitIsfoul.hotel.enums.BookingStatus;
import com.aitIsfoul.hotel.enums.BookingSubject;
import com.aitIsfoul.hotel.mapper.BookingMapper;
import com.aitIsfoul.hotel.services.BookingService;
import com.aitIsfoul.hotel.services.EmailService;
import com.aitIsfoul.hotel.services.PaymentService;
import com.aitIsfoul.hotel.utils.Utils;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImp implements BookingService {

    private final RoomDao roomDao;
    private final BookingDao bookingDao;
    private final CustomerDao customerDao;
    private final PaymentService paymentService;
    private final BookingMapper bookingMapper;
    private final EmailService emailService;

    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequest) throws StripeException {
        log.info("Starting booking creation process for client: {}", bookingRequest.getCustomerId());

        Room room = roomDao.findById(bookingRequest.getRoomId());
        log.debug("Room retrieved: ID = {}", room.getId());

        LocalDate checkInDate = LocalDate.parse(bookingRequest.getCheckIn());
        LocalDate checkOutDate = LocalDate.parse(bookingRequest.getCheckOut());

        log.info("Checking room availability for roomId: {}, from {} to {}", bookingRequest.getRoomId(), checkInDate, checkOutDate);
        boolean available = bookingDao.isRoomAvailable(bookingRequest.getRoomId(), checkInDate, checkOutDate);
        if (!available) {
            log.warn("Room not available for booking: roomId={}, checkIn={}, checkOut={}", bookingRequest.getRoomId(), checkInDate, checkOutDate);
            throw new IllegalStateException("Room not available");
        }

        Customer customer = customerDao.findById(bookingRequest.getCustomerId());
        log.debug("Customer retrieved: ID = {}, Email = {}", customer.getId(), customer.getEmail());
        long daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setCustomer(customer);
        booking.setCheckIn(checkInDate);
        booking.setCheckOut(checkOutDate);
        booking.setAdultsCount(bookingRequest.getAdultsCount());
        booking.setKidsCount(bookingRequest.getKidsCount());
        booking.setTotal(bookingRequest.getTotal());
        booking.setCurrency(bookingRequest.getCurrency());
        booking.setStatus(BookingStatus.PENDING_PAYMENT);
        booking.setBookingReference(Utils.generateBookingCode());
        booking.setFirstName(bookingRequest.getFirstName());
        booking.setPhoneNumber(bookingRequest.getPhoneNumber());
        booking.setEmail(bookingRequest.getEmail());
        booking.setTotalPrice(daysBetween * booking.getRoom().getPrice());
        booking.setIsActive("Y");
        booking = bookingDao.save(booking);
        log.info("Booking saved: Reference = {}", booking.getBookingReference());

        PaymentResponseDTO paymentResponse = paymentService.createPayment(booking);
        log.info("Payment initiated for bookingRef = {}, paymentUrl = {}", booking.getBookingReference(), paymentResponse.getPaymentUrl());

        BookingResponseDTO response = bookingMapper.toBookingResponseDTO(booking);
        response.setCheckoutUrl(paymentResponse.getPaymentUrl());
        emailService.sendPaymentPendingEmail(BookingSubject.BOOKING_PENDING.getSubject(),booking);
        emailService.notifyHotelOwner(BookingSubject.BOOKING_RECEIPT,booking);
        log.info("Booking creation process completed for bookingRef: {}", booking.getBookingReference());
        return response;
    }

    @Override
    public Booking getBookingById(String bookingId) {
        log.info("Fetching booking by ID: {}", bookingId);
        return bookingDao.getBookingById(bookingId);
    }

    @Override
    public BookingResponseDTO getBookingResponseDtoByRef(String bookingRef) {
        log.info("Fetching booking dto by reference: {}", bookingRef);
        Booking BookingReference = bookingDao.findByBookingReference(bookingRef);
        return bookingMapper.toBookingResponseDTO(BookingReference);
    }
    @Override
    public Booking getBookingByRef(String bookingRef) {
        log.info("Fetching booking by reference: {}", bookingRef);
        return bookingDao.findByBookingReference(bookingRef);
    }

    @Override
    public BookingDetailResponseDTO getBookingDetailByRef(String bookingRef) {
        log.info("Fetching booking detail by reference: {}", bookingRef);
        Booking booking = bookingDao.findByBookingReference(bookingRef);
        return bookingMapper.toBookingDetailResponseDTO(booking);
    }


    @Override
    public GenericPage<SearchBookingResponseDTO> searchBookings(SearchBookingRequestDTO searchBookingRequestDTO, Pageable pageable) {
        log.info("Starting booking search process with criteria: {}", searchBookingRequestDTO);

        Page<Booking> bookingPage = bookingDao.searchBookings(searchBookingRequestDTO, pageable);

        log.info("Found {} bookings matching search criteria", bookingPage.getTotalElements());

        Page<SearchBookingResponseDTO> searchBookingResponseDTOS = bookingMapper.bookingsPageTobookingsResponsePage(bookingPage);

        log.info("Mapped booking entities to booking response DTOs");

        return new GenericPage<>(searchBookingResponseDTOS);
    }



}
