package com.aitIsfoul.hotel.entity.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Location  {
    private String address;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
}
