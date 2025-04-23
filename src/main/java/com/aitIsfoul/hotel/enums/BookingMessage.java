package com.aitIsfoul.hotel.enums;

public enum BookingMessage {

    BOOKING_SUCCESS("Your booking has been confirmed."),
    BOOKING_FAILED("Booking failed. Please try again."),
    ROOM_NOT_AVAILABLE("The selected room is not available for the given dates."),
    INVALID_DATES("Check-in date must be before check-out date."),
    NOTIFICATION_EMAIL_SENT("A confirmation email has been sent to your inbox."),
    NOTIFICATION_SMS_SENT("A confirmation SMS has been sent to your phone."),
    USER_NOT_FOUND("User not found."),
    PAYMENT_PENDING("Booking is pending until payment is confirmed.");

    private final String message;

    BookingMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

