package com.aitIsfoul.hotel.entity;

import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "currency_id"))

public class Currency extends AbstractEntity {
    private String name;
    private String code;
}
