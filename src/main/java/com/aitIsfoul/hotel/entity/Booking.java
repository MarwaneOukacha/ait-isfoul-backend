package com.aitIsfoul.hotel.entity;
import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import com.aitIsfoul.hotel.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "booking_id"))
@Data
@ToString
public class Booking extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int adultsCount;
    private int kidsCount;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @Column(name = "Booking_reference", length = 100, unique = true)
    private String bookingReference;
    private String currency;
    private String firstName;
    private String email;
    private String phoneNumber;
    private Double total;
    private Double totalPrice;
}


