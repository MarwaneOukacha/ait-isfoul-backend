package com.aitIsfoul.hotel.mapper;

import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.RoomImage;
import com.aitIsfoul.hotel.entity.dto.RoomImageDTO;
import com.aitIsfoul.hotel.entity.dto.response.AddRoomResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.RoomDetailResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchRoomResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.UpdateRoomResponseDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    AddRoomResponseDTO roomToAddRoomResponseDTO(Room room);
    UpdateRoomResponseDTO roomToUpdateRoomResponseDTO(Room room);
    SearchRoomResponseDTO roomToSearchRoomResponseDTO(Room room);
    List<RoomImageDTO> toRoomImageDTOList(List<RoomImage> images);
    List<RoomImage> toRoomImageList(List<RoomImageDTO> dtos);
    default Page<SearchRoomResponseDTO> roomPageToSearchRoomResponseDTO(Page<Room> rooms) {
        return rooms.map(this::roomToSearchRoomResponseDTO);
    }

    RoomDetailResponseDTO fromRoomToRoomDetailResponseDTO(Room room);

}
