package com.aitIsfoul.hotel.mapper;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.response.BookingResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "bookingReference", target = "bookingReference")
    @Mapping(target = "checkoutUrl", ignore = true) // We set checkoutUrl manually later
    BookingResponseDTO toBookingResponseDTO(Booking booking);
}
