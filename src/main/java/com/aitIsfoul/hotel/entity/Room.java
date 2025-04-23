package com.aitIsfoul.hotel.entity;


import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import com.aitIsfoul.hotel.enums.RoomStatus;
import com.aitIsfoul.hotel.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "room_id"))
@Data
public class Room extends AbstractEntity {
    private String title;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    private Integer maxPeople;
    private Double price;
    private String description;
    private String facilitiesDesc;
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
}
