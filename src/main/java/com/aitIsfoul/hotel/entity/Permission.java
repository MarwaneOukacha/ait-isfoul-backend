package com.aitIsfoul.hotel.entity;


import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import com.aitIsfoul.hotel.enums.PermissionType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "permission_id"))
public class Permission extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    private PermissionType permissionName;
    private String isPossible;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}