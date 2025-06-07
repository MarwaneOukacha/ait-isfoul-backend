package com.aitIsfoul.hotel.entity;

import com.aitIsfoul.hotel.entity.model.AbstractUser;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Builder
@AttributeOverride(name = "id", column = @Column(name = "customer_id"))
@ToString
public class Customer extends AbstractUser {

}
