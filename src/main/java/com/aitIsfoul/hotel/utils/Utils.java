package com.aitIsfoul.hotel.utils;

import com.aitIsfoul.hotel.entity.Hotel;

import java.util.UUID;

public class Utils {
    public static String generateHotelCode(Hotel hotel) {

        String baseCode = hotel.getName().substring(0, Math.min(hotel.getName().length(), 3)) +
                hotel.getLocation().getCity().substring(0, Math.min(hotel.getLocation().getCity().length(), 3)) +
                System.currentTimeMillis();


        String uniqueCode = baseCode + "-" + UUID.randomUUID().toString().substring(0, 6);

        return uniqueCode.toUpperCase();
    }
}
