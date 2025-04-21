package com.aitIsfoul.hotel.entity;

import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@AttributeOverride(name = "id", column = @Column(name = "rule_id"))
public class Rule extends AbstractEntity {
    private String name;
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
}
