package com.aitIsfoul.hotel.dao;

import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.SearchRoomDTO;
import com.aitIsfoul.hotel.entity.dto.request.AddRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.CheckRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateRoomRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomDao {
    Page<Room> findAllWithCriteria(SearchRoomDTO searchRoomDTO, Pageable pageable);
    Room addRoom(AddRoomRequestDTO addRoomRequestDTO);
    Room updateRoom(UpdateRoomRequestDTO updateRoom);

    Room findById(String roomId);

}

