package com.aitIsfoul.hotel.entity;
import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import com.aitIsfoul.hotel.enums.BookingStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Booking extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User client;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int adultsCount;
    private int kidsCount;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @Column(name = "Booking_reference", length = 100, unique = true)
    private String bookingReference;
}


