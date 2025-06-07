package com.aitIsfoul.hotel.entity;

import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@AttributeOverride(name = "id", column = @Column(name = "notification_id"))
@ToString
public class Notification extends AbstractEntity {

    @Column(name = "reject_message")
    private String rejectMsg;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "sent_counter", nullable = false)
    private int sentCounter;

    @Column(name = "sent_max", nullable = false)
    private int sentMax;

    @Column(name = "entity_identifier", nullable = false)
    private String entityIden;

    @Column(name = "message", nullable = false)
    private String msg;

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private NotificationChannel channel;




}
