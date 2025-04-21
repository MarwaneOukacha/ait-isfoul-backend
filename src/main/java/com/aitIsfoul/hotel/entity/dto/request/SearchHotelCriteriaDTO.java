package com.aitIsfoul.hotel.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHotelCriteriaDTO {
    private String keyword; // matches name or location
    private Integer stars;
    private String ownerIden;
}
