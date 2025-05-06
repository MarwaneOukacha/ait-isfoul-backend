package com.aitIsfoul.hotel.entity;

import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "room_image")
public class RoomImage extends AbstractEntity {

    private String name;
    private String type;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
