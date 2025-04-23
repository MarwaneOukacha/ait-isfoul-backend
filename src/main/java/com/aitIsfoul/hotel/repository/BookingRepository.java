package com.aitIsfoul.hotel.repository;

import com.aitIsfoul.hotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID>, JpaSpecificationExecutor<Booking> {

    @Query("SELECT COUNT(b) = 0 FROM Booking b " +
            "WHERE b.room.id = :roomId " +
            "AND b.status IN ('PENDING_PAYMENT', 'CONFIRMED') " +
            "AND (" +
            "(:checkIn BETWEEN b.checkIn AND b.checkOut) " +
            "OR (:checkOut BETWEEN b.checkIn AND b.checkOut) " +
            "OR (b.checkIn BETWEEN :checkIn AND :checkOut)" +
            ")")
    boolean isRoomAvailable(@Param("roomId") UUID roomId,
                            @Param("checkIn") LocalDate checkIn,
                            @Param("checkOut") LocalDate checkOut);

    Optional<Booking> findByBookingReference(String reference);
}
