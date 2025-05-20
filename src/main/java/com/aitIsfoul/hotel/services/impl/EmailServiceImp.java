package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.ContactRequest;
import com.aitIsfoul.hotel.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImp implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${app.email-support}")
    private String emailSupport;

    @Override
    public void sendBookingConfirmation(String subject, String message, Booking booking) {
        long daysBetween = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());

        String plainText = "Dear " + booking.getCustomer().getFirstName() + " " + booking.getCustomer().getLastName() + ",\n\n"
                + "Thank you for choosing our services! Your booking has been confirmed.\n"
                + "Booking Reference: " + booking.getBookingReference() + "\n"
                + "Check-in: " + booking.getCheckIn() + "\n"
                + "Check-out: " + booking.getCheckOut() + "\n"
                + "Room Type: " + booking.getRoom().getRoomType() + "\n"
                + "Total Price: " + (booking.getRoom().getPrice() * daysBetween) + " " + booking.getCurrency() + "\n\n"
                + "Your payment is being processed and will be confirmed shortly.\n\n"
                + "Best regards,\n"
                + booking.getRoom().getHotel().getName() + " Hotel Team";

        String htmlContent = "<html>" +
                "<body>" +
                "<h2>Booking Confirmation</h2>" +
                "<p>Dear " + booking.getCustomer().getFirstName() + " " + booking.getCustomer().getLastName() + ",</p>" +
                "<p>Thank you for choosing our services! Your booking has been successfully confirmed. Here are the details:</p>" +

                "<h3>Booking Details:</h3>" +
                "<table>" +
                "<tr><td><strong>Booking Reference:</strong></td><td>" + booking.getBookingReference() + "</td></tr>" +
                "<tr><td><strong>Check-in:</strong></td><td>" + booking.getCheckIn() + "</td></tr>" +
                "<tr><td><strong>Check-out:</strong></td><td>" + booking.getCheckOut() + "</td></tr>" +
                "<tr><td><strong>Adults:</strong></td><td>" + booking.getAdultsCount() + "</td></tr>" +
                "<tr><td><strong>Children:</strong></td><td>" + booking.getKidsCount() + "</td></tr>" +
                "<tr><td><strong>Currency:</strong></td><td>" + booking.getCurrency() + "</td></tr>" +
                "<tr><td><strong>First Name:</strong></td><td>" + booking.getFirstName() + "</td></tr>" +
                "<tr><td><strong>Email:</strong></td><td>" + booking.getEmail() + "</td></tr>" +
                "<tr><td><strong>Phone number:</strong></td><td>" + booking.getPhoneNumber() + "</td></tr>" +
                "</table>" +

                "<h3>Room Details:</h3>" +
                "<table>" +
                "<tr><td><strong>Room Type:</strong></td><td>" + booking.getRoom().getRoomType() + "</td></tr>" +
                "<tr><td><strong>Description:</strong></td><td>" + booking.getRoom().getDescription() + "</td></tr>" +
                "<tr><td><strong>Price per Night:</strong></td><td>" + booking.getRoom().getPrice() + " " + booking.getCurrency() + "</td></tr>" +
                "<tr><td><strong>Total Price:</strong></td><td>" + (booking.getRoom().getPrice() * daysBetween) + " " + booking.getCurrency() + "</td></tr>" +
                "</table>" +

                "<h3>Payment Status:</h3>" +
                "<p>Your payment is being processed and will be confirmed shortly.</p>" +

                "<p>If you have any questions, feel free to contact us.</p>" +
                "<br>" +
                "<p>Best regards,</p>" +
                "<p>" + booking.getRoom().getHotel().getName() + " Hotel Team</p>" +
                "</body>" +
                "</html>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // Set From Address (use your actual domain!)
            helper.setFrom("noreply@yourdomain.com", booking.getRoom().getHotel().getName() + " Hotel");

            helper.setTo(booking.getEmail());
            helper.setSubject(subject);

            // Set both plain text and HTML version
            helper.setText(plainText, htmlContent);

            // Optional headers to improve deliverability
            mimeMessage.addHeader("X-Priority", "1");
            mimeMessage.addHeader("X-Mailer", "JavaMailer");
            mimeMessage.addHeader("List-Unsubscribe", "<mailto:unsubscribe@yourdomain.com>");

            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace(); // Or use proper logging
        }
    }
    @Override
    public void sendPaymentPendingEmail(String subject, Booking booking) {
        long daysBetween = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());

        String plainText = "Dear " + booking.getCustomer().getFirstName() + " " + booking.getCustomer().getLastName() + ",\n\n"
                + "Thank you for making a booking request. Your reservation is currently pending payment.\n"
                + "Booking Reference: " + booking.getBookingReference() + "\n"
                + "Check-in: " + booking.getCheckIn() + "\n"
                + "Check-out: " + booking.getCheckOut() + "\n"
                + "Room Type: " + booking.getRoom().getRoomType() + "\n"
                + "Total Price: " + (booking.getRoom().getPrice() * daysBetween) + " " + booking.getCurrency() + "\n\n"
                + "Please complete your payment to confirm the booking.\n\n"
                + "Best regards,\n"
                + booking.getRoom().getHotel().getName() + " Hotel Team";

        String htmlContent = "<html>" +
                "<body>" +
                "<h2>Payment Pending</h2>" +
                "<p>Dear " + booking.getCustomer().getFirstName() + " " + booking.getCustomer().getLastName() + ",</p>" +
                "<p>Thank you for your booking request. Your reservation is currently <strong>pending payment</strong>.</p>" +

                "<h3>Booking Details:</h3>" +
                "<table>" +
                "<tr><td><strong>Booking Reference:</strong></td><td>" + booking.getBookingReference() + "</td></tr>" +
                "<tr><td><strong>Check-in:</strong></td><td>" + booking.getCheckIn() + "</td></tr>" +
                "<tr><td><strong>Check-out:</strong></td><td>" + booking.getCheckOut() + "</td></tr>" +
                "<tr><td><strong>Adults:</strong></td><td>" + booking.getAdultsCount() + "</td></tr>" +
                "<tr><td><strong>Children:</strong></td><td>" + booking.getKidsCount() + "</td></tr>" +
                "<tr><td><strong>Currency:</strong></td><td>" + booking.getCurrency() + "</td></tr>" +
                "<tr><td><strong>First Name:</strong></td><td>" + booking.getFirstName() + "</td></tr>" +
                "<tr><td><strong>Email:</strong></td><td>" + booking.getEmail() + "</td></tr>" +
                "<tr><td><strong>Phone number:</strong></td><td>" + booking.getPhoneNumber() + "</td></tr>" +
                "</table>" +

                "<h3>Room Details:</h3>" +
                "<table>" +
                "<tr><td><strong>Room Type:</strong></td><td>" + booking.getRoom().getRoomType() + "</td></tr>" +
                "<tr><td><strong>Description:</strong></td><td>" + booking.getRoom().getDescription() + "</td></tr>" +
                "<tr><td><strong>Price per Night:</strong></td><td>" + booking.getRoom().getPrice() + " " + booking.getCurrency() + "</td></tr>" +
                "<tr><td><strong>Total Price:</strong></td><td>" + (booking.getRoom().getPrice() * daysBetween) + " " + booking.getCurrency() + "</td></tr>" +
                "</table>" +

                "<h3>Payment Instructions:</h3>" +
                "<p>Please complete your payment to confirm your reservation. If the payment is not received within the next 24 hours, your reservation may be cancelled.</p>" +

                "<p>If you have already paid, kindly ignore this message.</p>" +
                "<br>" +
                "<p>Best regards,</p>" +
                "<p>" + booking.getRoom().getHotel().getName() + " Hotel Team</p>" +
                "</body>" +
                "</html>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom("noreply@yourdomain.com", booking.getRoom().getHotel().getName() + booking.getRoom().getHotel().getName());
            helper.setTo(booking.getEmail());
            helper.setSubject(subject);
            helper.setText(plainText, htmlContent);

            // Optional headers
            mimeMessage.addHeader("X-Priority", "1");
            mimeMessage.addHeader("X-Mailer", "JavaMailer");
            mimeMessage.addHeader("List-Unsubscribe", "<mailto:unsubscribe@yourdomain.com>");

            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace(); // Use logger in production
        }
    }
    @Override
    public void sendContactEmail(ContactRequest request) {
        String plainText = "New Contact Us Message\n\n"
                + "Name: " + request.getName() + "\n"
                + "Email: " + request.getEmail() + "\n"
                + "Subject: " + request.getSubject() + "\n"
                + "Message:\n" + request.getMessage();

        String html = "<html><body>"
                + "<h2>New Contact Us Message</h2>"
                + "<p><strong>Name:</strong> " + request.getName() + "</p>"
                + "<p><strong>Email:</strong> " + request.getEmail() + "</p>"
                + "<p><strong>Subject:</strong> " + request.getSubject() + "</p>"
                + "<p><strong>Message:</strong><br/>" + request.getMessage().replace("\n", "<br/>") + "</p>"
                + "</body></html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("noreply@yourdomain.com", "Hotel");
            helper.setTo(emailSupport);
            helper.setSubject("New Contact Us: " + request.getSubject());
            helper.setText(plainText, html);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to send contact email", e);
        }
    }


}