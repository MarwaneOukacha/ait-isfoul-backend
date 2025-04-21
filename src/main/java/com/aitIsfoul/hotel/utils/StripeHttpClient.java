package com.aitIsfoul.hotel.utils;

import com.aitIsfoul.hotel.config.StripeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class StripeHttpClient {
    @Value("${stripe.secret-url}")
    private static String stripUrl;

    public static String createPaymentIntent(Map<String, String> paymentParams) {
        try {
            URL url = new URL(stripUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            StripeConfig config=new StripeConfig();
            // Set headers
            connection.setRequestProperty("Authorization", "Bearer " + config.getSecretKey());
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Prepare form params
            String formParams = paymentParams.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            // Write body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = formParams.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            int status = connection.getResponseCode();
            if (status == 200 || status == 201) {
                return new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            } else {
                String error = new String(connection.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                throw new RuntimeException("Stripe API error: " + error);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error creating payment intent: " + e.getMessage(), e);
        }
    }
}
