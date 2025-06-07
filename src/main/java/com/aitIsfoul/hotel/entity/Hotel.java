package com.aitIsfoul.hotel.entity;

import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import com.aitIsfoul.hotel.entity.model.Location;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@AttributeOverride(name = "id", column = @Column(name = "hotel_id"))
@ToString
public class Hotel extends AbstractEntity {
    private String name;
    @Embedded
    private Location location;
    private int stars;
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User owner;
    private String hotelIden;
}
