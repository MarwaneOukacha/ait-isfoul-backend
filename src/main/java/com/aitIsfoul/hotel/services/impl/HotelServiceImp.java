package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.dao.HotelDao;
import com.aitIsfoul.hotel.entity.Hotel;
import com.aitIsfoul.hotel.entity.dto.request.AddHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.AddHotelResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.UpdateHotelResponseDTO;
import com.aitIsfoul.hotel.mapper.HotelMapper;
import com.aitIsfoul.hotel.repository.HotelRepository;
import com.aitIsfoul.hotel.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HotelServiceImp implements HotelService {
    @Autowired
    private HotelDao hotelDao;
    @Autowired
    private HotelMapper hotelMapper;
    @Override
    public AddHotelResponseDTO addHotel(AddHotelRequestDTO addHotelRequestDTO) {
        log.info("HotelServiceImp :: addHotel {}", addHotelRequestDTO);
        Hotel hotel = hotelDao.addHotel(addHotelRequestDTO);
        log.info("HotelServiceImp :: addHotel response after HotelDao process {}", hotel);
        return hotelMapper.hotelToAddHotelResponseDTO(hotel);
    }

    @Override
    public UpdateHotelResponseDTO updateHotel(UpdateHotelRequestDTO updateHotelRequestDTO) {
        log.info("HotelServiceImpl :: updateHotel - Request received: {}", updateHotelRequestDTO);

        Hotel hotel = hotelDao.updateHotel(updateHotelRequestDTO);
        if (hotel == null) {
            log.error("HotelServiceImpl :: updateHotel - Failed to update hotel with Identifier: {}", updateHotelRequestDTO.getHotelIden());
            throw new RuntimeException("Failed to update hotel");
        }

        log.info("HotelServiceImpl :: updateHotel - Hotel updated successfully: {}", hotel.getId());

        UpdateHotelResponseDTO responseDTO = hotelMapper.hotelToUpdateHotelRequestDTO(hotel);
        log.info("HotelServiceImpl :: updateHotel - Mapped UpdateHotelResponseDTO: {}", responseDTO);

        return responseDTO;
    }

}
