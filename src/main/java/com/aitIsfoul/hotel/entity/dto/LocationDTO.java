package com.aitIsfoul.hotel.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO {
    private String address;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
}
