package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.dao.HotelDao;
import com.aitIsfoul.hotel.entity.Hotel;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.request.AddHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.SearchHotelCriteriaDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.AddHotelResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchHotelResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.UpdateHotelResponseDTO;
import com.aitIsfoul.hotel.mapper.HotelMapper;
import com.aitIsfoul.hotel.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public GenericPage<SearchHotelResponseDTO> getAllHotels(SearchHotelCriteriaDTO searchHotelCriteriaDTO, Pageable pageable) {
        log.info("HotelServiceImpl :: getAllHotels -Request received: {}", searchHotelCriteriaDTO);
        Page<Hotel> hotels = hotelDao.findAllWithCriteria(searchHotelCriteriaDTO, pageable);
        Page<SearchHotelResponseDTO> searchHotelResponseDTOS = hotelMapper.hotelPageToSearchHotelResponseDTO(hotels);
        return new GenericPage<>(searchHotelResponseDTOS);
    }
    @Override
    public SearchHotelResponseDTO getHotelByIdentifier(String identifier) {
        log.info("HotelServiceImpl :: getHotelByIdentifier - Request received: {}", identifier);
        Hotel hotel = hotelDao.getHotelByIdentifier(identifier);
        return hotelMapper.searchHotelResponsedtoToHotel(hotel);
    }

}
