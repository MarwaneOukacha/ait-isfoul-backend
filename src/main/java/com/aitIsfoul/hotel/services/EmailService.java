package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.ContactRequest;
import com.aitIsfoul.hotel.enums.BookingSubject;

public interface EmailService {
    void sendBookingConfirmation(String subject, String message, Booking booking);
    void sendPaymentPendingEmail(String subject, Booking booking);
    void sendContactEmail(ContactRequest request);

    void sendOtp(String email, String otp);

    void notifyHotelOwner(BookingSubject bookingSubject, Booking booking);

}
