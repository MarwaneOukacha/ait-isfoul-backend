package com.aitIsfoul.hotel.entity;

import java.util.UUID;


import com.aitIsfoul.hotel.entity.model.AbstractUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@ToString
public class User extends AbstractUser  {
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}