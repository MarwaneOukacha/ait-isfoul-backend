package com.aitIsfoul.hotel.entity;

import java.util.List;


import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import com.aitIsfoul.hotel.enums.ChannelType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "channel_id"))
public class NotificationChannel extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type", nullable = false, unique = true)
    private ChannelType channelType;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications ;

}