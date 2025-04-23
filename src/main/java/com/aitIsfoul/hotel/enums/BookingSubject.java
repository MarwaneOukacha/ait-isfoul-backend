package com.aitIsfoul.hotel.enums;
public enum BookingSubject {

    BOOKING_CONFIRMATION("Booking Confirmation"),
    BOOKING_FAILED("Booking Failed"),
    ROOM_UNAVAILABLE("Room Unavailable"),
    PAYMENT_CONFIRMATION("Payment Confirmation"),
    BOOKING_CANCELLED("Booking Cancelled"),
    BOOKING_REMINDER("Upcoming Booking Reminder"),
    BOOKING_RECEIPT("Your Booking Receipt"),
    CHECK_IN_REMINDER("Check-in Reminder"),
    CHECK_OUT_REMINDER("Check-out Reminder");

    private final String subject;

    BookingSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
