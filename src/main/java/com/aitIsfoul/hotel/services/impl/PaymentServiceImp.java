package com.aitIsfoul.hotel.services.impl;
import com.aitIsfoul.hotel.dao.BookingDao;
import com.aitIsfoul.hotel.dao.PaymentDao;
import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.response.PaymentResponseDTO;
import com.aitIsfoul.hotel.repository.PaymentRepository;
import com.aitIsfoul.hotel.services.PaymentService;
import com.aitIsfoul.hotel.utils.StripeHttpClient;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImp implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StripeHttpClient stripeHttpClient;
    private final PaymentDao paymentDao;
    private final BookingDao bookingDao;
    @Value("${app.succ-url}")
    private String succUrl;
    @Value("${app.cancel-url}")
    private String cancelUrl;
    @Override
    public PaymentResponseDTO createPayment(Booking booking) {
        long daysBetween = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());
        try {
            SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(succUrl)
                    .setCancelUrl(cancelUrl)
                    .setClientReferenceId(booking.getBookingReference())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L) // 1 Room booking, quantity usually 1 (you can customize if you want adults+kids)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(booking.getCurrency().toLowerCase())
                                                    .setUnitAmount((long) ( booking.getTotalPrice()* 100)) // Stripe needs price in cents
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(booking.getRoom().getTitle())
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);

            SessionCreateParams params = sessionBuilder.build();

            Session session = stripeHttpClient.createSession(params); // stripeHttpClient should call Stripe API

            if (session != null && session.getUrl() != null) {
                return PaymentResponseDTO.builder()
                        .paymentUrl(session.getUrl())
                        .build();
            }

            throw new RuntimeException("Stripe session is null");

        } catch (Exception e) {
            log.error("Stripe Session creation failed", e);
            throw new RuntimeException("Stripe Session creation error");
        }
    }
}
