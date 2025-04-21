package com.aitIsfoul.hotel.dao.impl;

import com.aitIsfoul.hotel.dao.BookingDao;
import com.aitIsfoul.hotel.dao.PaymentDao;
import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.Payment;
import com.aitIsfoul.hotel.entity.dto.request.PaymentConfirmationRequest;
import com.aitIsfoul.hotel.enums.PaymentMethode;
import com.aitIsfoul.hotel.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class paymentDaoImp implements PaymentDao {
    private final BookingDao bookingDao;
    @Override
    public Payment confirmPayment(PaymentConfirmationRequest confirmationRequest) {
        Booking booking =bookingDao.getBookingById(confirmationRequest.getBookingId());
        Payment payment = new Payment();
        payment.setBooking(booking);
        //payment.setPaymentAmount(confirmationRequest.getPaymentAmount());
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(PaymentMethode.DEBIT_CARD);
        //payment.setTransactionReference(confirmationRequest.getTransactionReference());
        //payment.setCurrency(confirmationRequest.getCurrency());

        //booking.setStatus(BookingStatus.SUCCESSFUL); // Example if payment success
        return null;
    }
}
