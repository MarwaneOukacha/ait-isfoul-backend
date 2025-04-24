package com.aitIsfoul.hotel.dao.impl;

import com.aitIsfoul.hotel.dao.HotelDao;
import com.aitIsfoul.hotel.dao.RoomDao;
import com.aitIsfoul.hotel.entity.Hotel;
import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.SearchRoomDTO;
import com.aitIsfoul.hotel.entity.dto.request.AddRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.CheckRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateRoomRequestDTO;
import com.aitIsfoul.hotel.enums.RoomStatus;
import com.aitIsfoul.hotel.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RoomDaoImp implements RoomDao {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelDao hotelDao;

    @Override
    public Page<Room> findAllWithCriteria(SearchRoomDTO searchRoomDTO, Pageable pageable) {
        log.info("Searching rooms with criteria: {}", searchRoomDTO);
        return roomRepository.findAllWithCriteria(searchRoomDTO,pageable);
    }

    @Override
    public Room addRoom(AddRoomRequestDTO addRoomRequestDTO) {
        log.info("Adding new room for hotel identifier: {}", addRoomRequestDTO.getHotelIden());

        Hotel hotelByIdentifier = hotelDao.getHotelByIdentifier(addRoomRequestDTO.getHotelIden());
        if (hotelByIdentifier == null) {
            log.error("Hotel not found with identifier: {}", addRoomRequestDTO.getHotelIden());
            throw new RuntimeException("Hotel not found with ID: " + addRoomRequestDTO.getHotelIden());
        }

        Room room = Room.builder()
                .hotel(hotelByIdentifier)
                .title(addRoomRequestDTO.getTitle())
                .roomType(addRoomRequestDTO.getRoomType())
                .maxPeople(addRoomRequestDTO.getMaxPeople())
                .price(addRoomRequestDTO.getPrice())
                .description(addRoomRequestDTO.getDescription())
                .facilitiesDesc(addRoomRequestDTO.getFacilitiesDesc())
                .roomStatus(RoomStatus.AVAILABLE) // Default: Available
                .build();
        room.setIsActive("Y");

        Room savedRoom = roomRepository.save(room);
        log.info("Room added successfully with ID: {}", savedRoom.getId());

        return savedRoom;
    }

    @Override
    public Room updateRoom(UpdateRoomRequestDTO updateRoom) {
        log.info("Updating room with ID: {}", updateRoom.getId());

        Optional<Room> optionalRoom = roomRepository.findById(UUID.fromString(updateRoom.getId()));
        if (optionalRoom.isEmpty()) {
            log.error("Room not found with ID: {}", updateRoom.getId());
            throw new RuntimeException("Room not found with ID: " + updateRoom.getId());
        }

        Room room = optionalRoom.get();

        if (updateRoom.getTitle() != null && !updateRoom.getTitle().isEmpty()) {
            room.setTitle(updateRoom.getTitle());
            log.debug("Updated title to: {}", updateRoom.getTitle());
        }

        if (updateRoom.getRoomType() != null) {
            room.setRoomType(updateRoom.getRoomType());
            log.debug("Updated room type to: {}", updateRoom.getRoomType());
        }

        if (updateRoom.getMaxPeople() > 0) {
            room.setMaxPeople(updateRoom.getMaxPeople());
            log.debug("Updated max people to: {}", updateRoom.getMaxPeople());
        }

        if (updateRoom.getPrice() != null) {
            room.setPrice(updateRoom.getPrice());
            log.debug("Updated price to: {}", updateRoom.getPrice());
        }

        if (updateRoom.getDescription() != null && !updateRoom.getDescription().isEmpty()) {
            room.setDescription(updateRoom.getDescription());
            log.debug("Updated description.");
        }

        if (updateRoom.getFacilitiesDesc() != null && !updateRoom.getFacilitiesDesc().isEmpty()) {
            room.setFacilitiesDesc(updateRoom.getFacilitiesDesc());
            log.debug("Updated facilities description.");
        }

        if (updateRoom.getIsActive() != null && !updateRoom.getIsActive().isEmpty()) {
            room.setIsActive(updateRoom.getIsActive());
            log.debug("Updated isActive to: {}", updateRoom.getIsActive());
        }

        if (updateRoom.getRoomStatus() != null) {
            room.setRoomStatus(updateRoom.getRoomStatus());
            log.debug("Updated room status to: {}", updateRoom.getRoomStatus());
        }

        Room updatedRoom = roomRepository.save(room);
        log.info("Room updated successfully with ID: {}", updatedRoom.getId());

        return updatedRoom;
    }

    @Override
    public Room findById(String roomId) {
        return  roomRepository.findById(UUID.fromString(roomId))
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
    }


}
