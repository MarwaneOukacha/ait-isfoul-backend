package com.aitIsfoul.hotel.entity;

import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "room_image_id"))
public class RoomImages extends AbstractEntity {
    private String name;
    private String type;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
}
