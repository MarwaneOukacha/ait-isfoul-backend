package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.request.BookingRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImp implements EmailService{

    private final JavaMailSender mailSender;
    @Override
    public void sendBookingConfirmation(String toEmail, String subject, String message, Booking booking) {
        String emailContent = "<html>" +
                "<body>" +
                "<h2>Booking Confirmation</h2>" +
                "<p>Dear " + booking.getClient().getFirstName()+" "+booking.getClient().getLastName() + ",</p>" +
                "<p>Thank you for choosing our services! Your booking has been successfully confirmed. Here are the details of your booking:</p>" +

                "<h3>Booking Details:</h3>" +
                "<table>" +
                "<tr><td><strong>Booking Reference:</strong></td><td>" + booking.getBookingReference() + "</td></tr>" +
                "<tr><td><strong>Check-in:</strong></td><td>" + booking.getCheckIn() + "</td></tr>" +
                "<tr><td><strong>Check-out:</strong></td><td>" + booking.getCheckOut() + "</td></tr>" +
                "<tr><td><strong>Adults:</strong></td><td>" + booking.getAdultsCount() + "</td></tr>" +
                "<tr><td><strong>Children:</strong></td><td>" + booking.getKidsCount() + "</td></tr>" +
                "<tr><td><strong>Currency:</strong></td><td>" + booking.getCurrency() + "</td></tr>" +
                "</table>" +

                "<h3>Room Details:</h3>" +
                "<table>" +
                "<tr><td><strong>Room Type:</strong></td><td>" + booking.getRoom().getRoomType() + "</td></tr>" +
                "<tr><td><strong>Room Description:</strong></td><td>" + booking.getRoom().getDescription() + "</td></tr>" +
                "<tr><td><strong>Price per Night:</strong></td><td>" + booking.getRoom().getPrice() + " " + booking.getCurrency() + "</td></tr>" +
                "</table>" +

                "<h3>Payment Details:</h3>" +
                "<p>Your payment is still pending.It will be confirmed just in few minutes</p>" +
                //"<a href=\"" + bookingRequest.getCheckoutUrl() + "\">Complete Payment</a>" +

                "<p>If you have any questions or need assistance, feel free to contact us.</p>" +
                "<br>" +
                "<p>Best regards,</p>" +
                "<p>"+booking.getRoom().getHotel().getName()+" Hotel Team</p>" +
                "</body>" +
                "</html>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(emailContent, true);  // true for HTML content
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // Log the exception
        }
    }


}
