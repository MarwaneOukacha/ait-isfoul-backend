package com.aitIsfoul.hotel.entity.dto.request;

import com.aitIsfoul.hotel.entity.dto.RoomImageDTO;
import com.aitIsfoul.hotel.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AddRoomRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @NotNull(message = "Room type is required")

    private RoomType roomType;

    @Positive(message = "Maximum number of people must be greater than 0")
    private int maxPeople;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Facilities description is required")
    @Size(max = 300, message = "Facilities description must not exceed 300 characters")
    private String facilitiesDesc;
    @NotBlank(message = "Hotel identifier is required")
    private String hotelIden;
    @NotBlank(message = "Size is required")
    private String size;
    //@NotBlank(message = "Images are required")
    private List<RoomImageDTO> images;
}
