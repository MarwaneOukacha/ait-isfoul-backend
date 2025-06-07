package com.aitIsfoul.hotel.entity.dto;

import com.aitIsfoul.hotel.enums.RoomStatus;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class SearchRoomDTO {
   private String keyword;
   private RoomStatus roomStatus;
   private Double minPrice;
   private Double maxPrice;
   private Integer maxPeople;
   private String hotelRef;
   private String checkIn;
   private String checkOut;
}
