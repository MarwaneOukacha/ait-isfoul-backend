package com.aitIsfoul.hotel.entity;

import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "room_image")
@ToString
public class RoomImage extends AbstractEntity {

    private String name;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
