package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.Room;

public interface EmailService {
    void sendBookingConfirmation(String toEmail, String subject, String message, Booking booking);
}
