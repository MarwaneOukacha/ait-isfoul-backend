package com.aitIsfoul.hotel.dao.impl;

import com.aitIsfoul.hotel.dao.HotelDao;
import com.aitIsfoul.hotel.dao.UserDao;
import com.aitIsfoul.hotel.entity.Hotel;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.request.AddHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.SearchHotelCriteriaDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateHotelRequestDTO;
import com.aitIsfoul.hotel.entity.model.Location;
import com.aitIsfoul.hotel.mapper.HotelMapper;
import com.aitIsfoul.hotel.repository.HotelRepository;
import com.aitIsfoul.hotel.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class HotelDaoImp implements HotelDao {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HotelMapper hotelMapper;

    @Override
    public Hotel addHotel(AddHotelRequestDTO addHotelRequestDTO) {
        log.info("HotelDaoImp :: addHotel {}", addHotelRequestDTO);

        User userById = userDao.getUserById(addHotelRequestDTO.getOwnerId());

        if (userById == null) {
            log.error("HotelDaoImp :: addHotel User not found");
            throw new RuntimeException("User not found");
        }

        Hotel hotel = hotelMapper.addHotelRequestDtoToHotel(addHotelRequestDTO);
        String hotelCode = Utils.generateHotelCode(hotel);
        hotel.setOwner(userById);
        hotel.setIsActive("Y");
        hotel.setHotelIden(hotelCode);


        return hotelRepository.save(hotel);
    }



    @Override
    public Hotel updateHotel(UpdateHotelRequestDTO updateHotelRequestDTO) {
        log.info("HotelDaoImp :: updateHotel request received -> {}", updateHotelRequestDTO);

        Hotel hotel = getHotelByIdentifier(updateHotelRequestDTO.getHotelIden());

        if (updateHotelRequestDTO.getName() != null) {
            log.info("Updating hotel name from '{}' to '{}'", hotel.getName(), updateHotelRequestDTO.getName());
            hotel.setName(updateHotelRequestDTO.getName());
        }

        if (updateHotelRequestDTO.getStars() > 0) {
            log.info("Updating hotel stars from '{}' to '{}'", hotel.getStars(), updateHotelRequestDTO.getStars());
            hotel.setStars(updateHotelRequestDTO.getStars());
        }

        if (updateHotelRequestDTO.getLocation() != null) {
            log.info("Updating hotel location from '{}' to '{}'", hotel.getLocation(), updateHotelRequestDTO.getLocation());
            Location updatedLocation = hotelMapper.toLocation(updateHotelRequestDTO.getLocation());
            hotel.setLocation(updatedLocation);
        }

        Hotel updated = hotelRepository.save(hotel);
        log.info("Hotel updated successfully: {}", updated);

        return updated;
    }

    @Override
    public Hotel getHotelByIdentifier(String identifier) {
        return hotelRepository.findByHotelIden(identifier).orElseThrow(()->new EntityNotFoundException("Hotel not found with identifier: " + identifier));
    }


    @Override
    public Page<Hotel> findAllWithCriteria(SearchHotelCriteriaDTO criteria, Pageable pageable) {
        log.info("HotelDaoImp :: findAllWithCriteria  -> {}", criteria);
        return hotelRepository.findAllWithCriteria(criteria,pageable);
    }
}
