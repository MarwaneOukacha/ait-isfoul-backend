package com.aitIsfoul.hotel.dao;

import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.SearchRoomDTO;
import com.aitIsfoul.hotel.entity.dto.request.AddRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateRoomRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomDao {
    Page<Room> findAllWithCriteria(SearchRoomDTO searchRoomDTO, Pageable pageable);
    Room addRoom(AddRoomRequestDTO addRoomRequestDTO);
    Room updateRoom(UpdateRoomRequestDTO updateRoom);
}

