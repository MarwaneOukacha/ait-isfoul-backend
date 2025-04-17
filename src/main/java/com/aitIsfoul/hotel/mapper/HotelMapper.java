package com.aitIsfoul.hotel.mapper;

import com.aitIsfoul.hotel.entity.Hotel;
import com.aitIsfoul.hotel.entity.RoomImage;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.LocationDTO;
import com.aitIsfoul.hotel.entity.dto.RoomImageDTO;
import com.aitIsfoul.hotel.entity.dto.request.AddHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.AddHotelResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchHotelResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateHotelResponseDTO;
import com.aitIsfoul.hotel.entity.model.Location;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.Base64;
import java.util.List;
@Mapper(componentModel = "spring")
public interface HotelMapper {

    // DTO → Entity
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "images", source = "images")
    Hotel addHotelRequestDtoToHotel(AddHotelRequestDTO dto);

    SearchHotelResponseDTO searchHotelResponsedtoToHotel(Hotel hotel);
    // Entity → DTO
    AddHotelResponseDTO hotelToAddHotelResponseDTO(Hotel hotel);

    Location toLocation(LocationDTO dto);
    LocationDTO toLocationDTO(Location location);

    default Page<SearchHotelResponseDTO> hotelPageToSearchHotelResponseDTO(Page<Hotel> hotels) {
        return hotels.map(this::searchHotelResponsedtoToHotel);
    }
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "data", source = "data", qualifiedByName = "base64ToBytes")
    RoomImage toRoomImage(RoomImageDTO dto);

    @Mapping(target = "data", source = "data", qualifiedByName = "bytesToBase64")
    RoomImageDTO toRoomImageDTO(RoomImage image);

    List<RoomImage> toRoomImageList(List<RoomImageDTO> dtos);
    List<RoomImageDTO> toRoomImageDTOList(List<RoomImage> images);

    @Named("base64ToBytes")
    static byte[] mapBase64ToBytes(String base64) {
        return base64 != null ? Base64.getDecoder().decode(base64) : null;
    }

    @Named("bytesToBase64")
    static String mapBytesToBase64(byte[] data) {
        return data != null ? Base64.getEncoder().encodeToString(data) : null;
    }

    @AfterMapping
    default void linkImages(@MappingTarget Hotel hotel) {
        if (hotel.getImages() != null) {
            hotel.getImages().forEach(image -> image.setHotel(hotel));
        }
    }

    // Entity to Update DTO
    UpdateHotelResponseDTO hotelToUpdateHotelRequestDTO(Hotel hotel);
}
