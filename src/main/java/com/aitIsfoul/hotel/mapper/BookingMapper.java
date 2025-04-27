package com.aitIsfoul.hotel.mapper;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.dto.response.BookingResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchBookingResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchCustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "bookingReference", target = "bookingReference")
    @Mapping(target = "checkoutUrl", ignore = true) // We set checkoutUrl manually later
    BookingResponseDTO toBookingResponseDTO(Booking booking);

    SearchBookingResponseDTO toSearchBookingResponseDTO(Booking booking);
    default Page<SearchBookingResponseDTO> bookingsPageTobookingsResponsePage(Page<Booking> bookingPage) {
        return bookingPage.map(this::toSearchBookingResponseDTO);
    }
}

