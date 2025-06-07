package com.aitIsfoul.hotel.entity.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LocationDTO {
    private String address;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
}
