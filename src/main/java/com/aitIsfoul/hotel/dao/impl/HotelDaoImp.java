package com.aitIsfoul.hotel.dao.impl;

import com.aitIsfoul.hotel.dao.HotelDao;
import com.aitIsfoul.hotel.entity.Hotel;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.request.AddHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.SearchHotelCriteriaDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateHotelRequestDTO;
import com.aitIsfoul.hotel.repository.HotelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class HotelDaoImp implements HotelDao {
    private HotelRepository hotelRepository;
    @Override
    public Hotel addUser(AddHotelRequestDTO addHotelRequestDTO) {
        return null;
    }

    @Override
    public Hotel updateUser(UpdateHotelRequestDTO updateHotelRequestDTO) {
        return null;
    }

    @Override
    public User getUserByIdentifier(String identifier) {
        return null;
    }

    @Override
    public Page<Hotel> findAllWithCriteria(SearchHotelCriteriaDTO criteria, Pageable pageable) {
        return null;
    }
}
