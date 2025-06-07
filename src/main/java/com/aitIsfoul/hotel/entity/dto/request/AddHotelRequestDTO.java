package com.aitIsfoul.hotel.entity.dto.request;

import com.aitIsfoul.hotel.entity.dto.LocationDTO;
import com.aitIsfoul.hotel.entity.dto.RoomImageDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddHotelRequestDTO {
    @NotBlank(message = "Hotel name is required")
    private String name;

    @Valid
    @NotNull(message = "Location is required")
    private LocationDTO location;

    @Min(1)
    @Max(5)
    private int stars;

    @NotNull(message = "Owner ID is required")
    private String ownerId;
}
