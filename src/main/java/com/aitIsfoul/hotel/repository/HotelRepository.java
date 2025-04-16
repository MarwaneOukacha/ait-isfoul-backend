package com.aitIsfoul.hotel.repository;

import com.aitIsfoul.hotel.entity.Hotel;
import com.aitIsfoul.hotel.entity.dto.request.SearchHotelCriteriaDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID>, JpaSpecificationExecutor<Hotel> {
    Optional<Hotel> findByHotelIden(String hotelIden);
    default Page<Hotel> findAllWithCriteria(SearchHotelCriteriaDTO criteria, Pageable pageable) {
        return findAll((Specification<Hotel>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
                String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), pattern),
                        cb.like(cb.lower(root.get("location").get("city")), pattern),
                        cb.like(cb.lower(root.get("location").get("country")), pattern)
                ));
            }

            if (criteria.getStars() != null) {
                predicates.add(cb.equal(root.get("stars"), criteria.getStars()));
            }

            if (criteria.getOwnerIden() != null) {
                predicates.add(cb.equal(root.get("owner").get("iden"), criteria.getOwnerIden()));
            }

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            query.orderBy(cb.desc(root.get("created"))); // Assuming 'created' field exists in AbstractEntity

            return query.getRestriction();
        }, pageable);
    }

}
