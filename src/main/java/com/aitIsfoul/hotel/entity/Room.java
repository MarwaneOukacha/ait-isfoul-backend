package com.aitIsfoul.hotel.entity;


import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import com.aitIsfoul.hotel.enums.RoomType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "room_id"))
public class Room extends AbstractEntity {
    private String title;
    private RoomType roomType;
    private int maxPeople;
    private Double price;
    private String description;
    private String facilitiesDesc;
}
