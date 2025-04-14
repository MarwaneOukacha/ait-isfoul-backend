package com.aitIsfoul.hotel.entity;

import com.aitIsfoul.hotel.entity.model.AbstractUser;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client extends AbstractUser {

}
