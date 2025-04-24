package com.aitIsfoul.hotel.repository;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.dto.request.SearchBookingRequestDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    default Specification<Booking> withCriteria(SearchBookingRequestDTO criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getBookingReference() != null) {
                predicates.add(cb.equal(root.get("bookingReference"), criteria.getBookingReference()));
            }
            if (criteria.getCheckIn() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("checkIn"), criteria.getCheckIn()));
            }
            if (criteria.getCheckOut() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("checkOut"), criteria.getCheckOut()));
            }
            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }
            if (criteria.getClientName() != null) {
                predicates.add(cb.like(cb.lower(root.get("client").get("fullName")), "%" + criteria.getClientName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
