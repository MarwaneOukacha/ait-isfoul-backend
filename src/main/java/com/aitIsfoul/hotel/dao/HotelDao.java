package com.aitIsfoul.hotel.dao;

import com.aitIsfoul.hotel.entity.Hotel;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.SearchUserCriteria;
import com.aitIsfoul.hotel.entity.dto.request.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelDao {
    Hotel addHotel(AddHotelRequestDTO addHotelRequestDTO);

    Hotel updateHotel(UpdateHotelRequestDTO updateHotelRequestDTO);
    Hotel getHotelByIdentifier(String identifier);

    Page<Hotel> findAllWithCriteria(SearchHotelCriteriaDTO criteria, Pageable pageable);
}
