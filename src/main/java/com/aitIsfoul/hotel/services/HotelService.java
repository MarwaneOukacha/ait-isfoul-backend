package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.Hotel;
import com.aitIsfoul.hotel.entity.dto.request.AddHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.AddHotelResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.UpdateHotelResponseDTO;

public interface HotelService {
    AddHotelResponseDTO addHotel(AddHotelRequestDTO addHotelRequestDTO);
    UpdateHotelResponseDTO updateHotel(UpdateHotelRequestDTO updateHotelRequestDTO);
}
