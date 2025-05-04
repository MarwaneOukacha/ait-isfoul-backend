package com.aitIsfoul.hotel.entity.dto;

import com.aitIsfoul.hotel.enums.RoomStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchRoomDTO {
   private String keyword;
   private RoomStatus roomStatus;
   private Double minPrice;
   private Double maxPrice;
   private Integer maxPeople;
   private String hotelRef;
}
