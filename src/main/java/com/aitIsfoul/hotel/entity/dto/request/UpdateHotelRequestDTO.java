package com.aitIsfoul.hotel.entity.dto.request;

import com.aitIsfoul.hotel.entity.dto.LocationDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateHotelRequestDTO {
    @NotNull(message = "Hotel ID is required")
    private String hotelIden;

    private String name;
    private LocationDTO location;
    @Max(5)
    private int stars;

}
