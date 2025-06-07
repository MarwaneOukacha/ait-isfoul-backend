package com.aitIsfoul.hotel.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.aitIsfoul.hotel.entity.model.AbstractEntity;
import com.aitIsfoul.hotel.enums.PaymentMethode;
import com.aitIsfoul.hotel.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@AttributeOverride(name = "id", column = @Column(name = "payment_id"))
@ToString
public class Payment extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking; // Links payment to an booking

    @Column(name = "payment_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 20, nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_method", length = 50, nullable = false)
    private PaymentMethode paymentMethod;

    @Column(name = "transaction_reference", length = 100, unique = true)
    private String transactionReference;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;
    @OneToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

}