package com.aitIsfoul.hotel.repository;

import com.aitIsfoul.hotel.entity.Booking;
import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.request.SearchBookingRequestDTO;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    default Page<Booking> findAllWithCriteria(SearchBookingRequestDTO criteria, Pageable pageable) {
        return this.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getCheckIn() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("checkIn"), LocalDate.parse(criteria.getCheckIn())));
            }
            if (criteria.getCheckOut() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("checkOut"), LocalDate.parse(criteria.getCheckOut())));
            }
            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String keywordLike = "%" + criteria.getKeyword().toLowerCase() + "%";

                Join<Booking, Customer> clientJoin = root.join("client", JoinType.LEFT);
                Join<Booking, Room> roomJoin = root.join("room", JoinType.LEFT);

                Predicate bookingRefPredicate = cb.like(cb.lower(root.get("bookingReference")), keywordLike);
                Predicate clientNamePredicate = cb.like(cb.lower(clientJoin.get("name")), keywordLike);
                Predicate roomNamePredicate = cb.like(cb.lower(roomJoin.get("name")), keywordLike);

                predicates.add(cb.or(bookingRefPredicate, clientNamePredicate, roomNamePredicate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }


}
