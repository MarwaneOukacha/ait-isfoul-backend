package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.dao.BookingDao;
import com.aitIsfoul.hotel.dao.PaymentDao;
import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.enums.BookingStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/stripe")
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookController {

    private final PaymentDao paymentDao;
    private final BookingDao bookingDao;
    @Value("${stripe.secret-key}")
    private static String endpointSecret;


    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        log.info("Start handleStripeWebhook payload {} ", payload);
        Event event;

        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, endpointSecret
            );
        } catch (SignatureVerificationException e) {
            log.error("Webhook signature verification failed.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            if (session != null) {
                String bookingReference = session.getClientReferenceId(); // If you set it when creating the session
                log.info("Payment succeeded for booking reference: {}", bookingReference);
                log.info("payload : {}", payload);
                log.info("sigHeader : {}", sigHeader);
                // Find booking using the booking reference
                Booking booking = bookingDao.findByBookingReference(bookingReference);
                booking.setStatus(BookingStatus.SUCCESSFUL);
                bookingDao.save(booking);

                log.info("Booking {} updated to CONFIRMED", bookingReference);
            }
        }else {
            log.error("Webhook signature verification failed. {}",payload);
        }

        return ResponseEntity.ok("Webhook received");
    }
}
