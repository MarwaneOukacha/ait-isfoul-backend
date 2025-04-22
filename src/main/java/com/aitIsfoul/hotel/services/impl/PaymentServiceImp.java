package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.dao.BookingDao;
import com.aitIsfoul.hotel.dao.PaymentDao;
import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.request.PaymentRequest;
import com.aitIsfoul.hotel.entity.dto.response.PaymentResponseDTO;
import com.aitIsfoul.hotel.enums.PaymentMethode;
import com.aitIsfoul.hotel.repository.BookingRepository;
import com.aitIsfoul.hotel.repository.PaymentRepository;
import com.aitIsfoul.hotel.services.PaymentService;
import com.aitIsfoul.hotel.utils.StripeHttpClient;
import com.stripe.exception.StripeException;
import com.stripe.model.billingportal.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImp implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final StripeHttpClient stripeHttpClient;
    private final PaymentDao paymentDao;
    private final BookingDao bookingDao;


    @Override
    public PaymentResponseDTO createPayment(PaymentRequest paymentRequest) {
        Booking booking =bookingDao.getBookingById(paymentRequest.getBookingId());

        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("")
                .setCancelUrl("")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity((long) (booking.getAdultsCount() + booking.getKidsCount()))
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("USD")
                                                .setUnitAmount(booking.getRoom().getPrice().longValue())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(booking.getRoom().getTitle())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                );

        if (paymentRequest.getPaymentMethode() == PaymentMethode.CARD) {
            sessionBuilder.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);
        }
        SessionCreateParams params = sessionBuilder.build();
        Session session = null;
        try {
            session = Session.create((Map<String, Object>) params);
        }catch (Exception e){
            log.error("");
        }
        if(session.getUrl()!=null){
            return PaymentResponseDTO.
                    builder()
                    .paymentUrl(session.getUrl())
                    .build();
        }

        throw new RuntimeException("Strip session is null");

    }

}
