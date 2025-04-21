package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.SearchRoomDTO;
import com.aitIsfoul.hotel.entity.dto.request.AddRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.AddRoomResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchRoomResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.UpdateRoomResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    GenericPage<SearchRoomResponseDTO> getRooms(SearchRoomDTO searchRoomDTO, Pageable pageable);
    AddRoomResponseDTO addRoom(AddRoomRequestDTO addRoomRequestDTO);
    UpdateRoomResponseDTO updateRoom(UpdateRoomRequestDTO updateRoom);
}
