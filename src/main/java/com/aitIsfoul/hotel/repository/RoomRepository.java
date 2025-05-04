package com.aitIsfoul.hotel.repository;

import com.aitIsfoul.hotel.entity.Room;
import com.aitIsfoul.hotel.entity.dto.SearchRoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.criteria.Predicate;

public interface RoomRepository extends JpaRepository<Room, UUID> , JpaSpecificationExecutor<Room> {
    default Page<Room> findAllWithCriteria(SearchRoomDTO criteria, Pageable pageable) {
        return findAll((Specification<Room>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (criteria.getHotelRef() != null) {
                predicates.add(cb.equal(root.get("hotel").get("hotelIden"), criteria.getHotelRef()));
            }
            if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
                String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), pattern),
                        cb.like(cb.lower(root.get("description")), pattern),
                        cb.like(cb.lower(root.get("facilitiesDesc")), pattern)
                ));
            }

            if (criteria.getRoomStatus() != null) {
                predicates.add(cb.equal(root.get("roomStatus"), criteria.getRoomStatus()));
            }

            if (criteria.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
            }

            if (criteria.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
            }

            if (criteria.getMaxPeople()!=null && criteria.getMaxPeople() > 0) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("maxPeople"), criteria.getMaxPeople()));
            }

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            query.orderBy(cb.desc(root.get("created"))); // Assuming AbstractEntity has 'created' timestamp

            return query.getRestriction();
        }, pageable);
    }

}
