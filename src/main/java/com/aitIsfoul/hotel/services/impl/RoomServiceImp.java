package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.dao.BookingDao;
import com.aitIsfoul.hotel.dao.RoomDao;
import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.SearchRoomDTO;
import com.aitIsfoul.hotel.entity.dto.request.AddRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.CheckRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.AddRoomResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.RoomDetailResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchRoomResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.UpdateRoomResponseDTO;
import com.aitIsfoul.hotel.mapper.RoomMapper;
import com.aitIsfoul.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImp implements RoomService {


    private final RoomMapper roomMapper;
    private final BookingDao bookingDao;


    @Autowired
    private RoomDao roomDao;

    @Override
    public GenericPage<SearchRoomResponseDTO> getRooms(SearchRoomDTO searchRoomDTO, Pageable pageable) {
        log.info("Fetching rooms with criteria: {}", searchRoomDTO);

        Page<Room> allWithCriteria = roomDao.findAllWithCriteria(searchRoomDTO, pageable);
        Page<SearchRoomResponseDTO> searchRoomResponseDTOS = roomMapper.roomPageToSearchRoomResponseDTO(allWithCriteria);

        log.info("Found {} rooms matching the criteria.", allWithCriteria.getTotalElements());

        return new GenericPage<>(searchRoomResponseDTOS);
    }

    @Override
    public AddRoomResponseDTO addRoom(AddRoomRequestDTO addRoomRequestDTO) {
        log.info("Adding a new room with details: {}", addRoomRequestDTO);

        Room room = roomDao.addRoom(addRoomRequestDTO);
        AddRoomResponseDTO response = roomMapper.roomToAddRoomResponseDTO(room);

        log.info("Room added successfully with ID: {}", room.getId());

        return response;
    }

    @Override
    public UpdateRoomResponseDTO updateRoom(UpdateRoomRequestDTO updateRoom) {
        log.info("Updating room with ID: {}", updateRoom.getId());

        Room room = roomDao.updateRoom(updateRoom);
        UpdateRoomResponseDTO response = roomMapper.roomToUpdateRoomResponseDTO(room);

        log.info("Room updated successfully with ID: {}", room.getId());

        return response;
    }

    @Override
        public Boolean isRoomAvailable(CheckRoomRequestDTO checkRoomRequestDTO) {
        log.info("Check room if is available : {}", checkRoomRequestDTO);
        LocalDate checkInDate = LocalDate.parse(checkRoomRequestDTO.getCheckIn());
        LocalDate checkOutDate = LocalDate.parse(checkRoomRequestDTO.getCheckOut());
        String roomId=checkRoomRequestDTO.getRoomId();
        return bookingDao.isRoomAvailable(roomId,checkInDate,checkOutDate);
    }

    @Override
    public RoomDetailResponseDTO getRoomDetail(String id) {
        log.info("Get room details with id : {}", id);
        Room room=roomDao.findById(id);
        return roomMapper.fromRoomToRoomDetailResponseDTO(room);
    }
}

