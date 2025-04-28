package com.aitIsfoul.hotel.entity;

import com.aitIsfoul.hotel.entity.model.AbstractUser;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AttributeOverride(name = "id", column = @Column(name = "customer_id"))
public class Customer extends AbstractUser {

}
