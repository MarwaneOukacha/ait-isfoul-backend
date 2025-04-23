package com.aitIsfoul.hotel.utils;

import com.aitIsfoul.hotel.entity.Hotel;

import java.util.UUID;

public class Utils {
    public static String generateHotelCode(Hotel hotel) {
        // Generate a numeric base using the hotel name and city hash codes
        int hotelNameHash = Math.abs(hotel.getName().hashCode());
        int cityHash = Math.abs(hotel.getLocation().getCity().hashCode());

        // Take the last 3 digits of each hash
        int hotelNamePart = hotelNameHash % 1000;
        int cityPart = cityHash % 1000;

        // Add current timestamp (last 6 digits for uniqueness)
        long timestampPart = System.currentTimeMillis() % 1000000;

        // Build the final numeric code
        String code = String.format("%03d%03d%06d", hotelNamePart, cityPart, timestampPart);

        return code;
    }
    public static String generateBookingCode() {
        // Get the current timestamp (last 8 digits)
        long timestampPart = System.currentTimeMillis() % 100000000L;

        // Generate a random 4-digit number
        int randomPart = (int)(Math.random() * 9000) + 1000; // Always 4 digits (1000–9999)

        // Combine to create a unique booking code
        String code = String.format("%08d%04d", timestampPart, randomPart);

        return code;
    }


}
