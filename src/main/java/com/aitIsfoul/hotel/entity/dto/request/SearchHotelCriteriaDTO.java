package com.aitIsfoul.hotel.entity.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchHotelCriteriaDTO {
    private String keyword; // matches name or location
    private Integer stars;
    private String ownerIden;
}
