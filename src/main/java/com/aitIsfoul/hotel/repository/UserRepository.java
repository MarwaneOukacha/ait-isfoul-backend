package com.aitIsfoul.hotel.repository;

import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.SearchUserCriteria;
import com.aitIsfoul.hotel.enums.UserStatus;
import jakarta.persistence.criteria.Expression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> , JpaSpecificationExecutor<User> {


    Optional<User> findByIden(String iden);
    Optional<User> findByUsername(String username);

    Optional<User> getUserByIden(String iden);

    default Page<User> findAllWithCriteria(SearchUserCriteria criteria, Pageable pageable) {
        return findAll((Specification<User>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null) {
                String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("firstName")), pattern),
                        cb.like(cb.lower(root.get("lastName")), pattern),
                        cb.like(cb.lower(root.get("email")), pattern),
                        cb.like(cb.lower(root.get("phoneNumber")), pattern)
                ));
            }

            if (criteria.getRoleName() != null) {
                predicates.add(cb.equal(root.get("role").get("roleName"), criteria.getRoleName()));
            }

            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            if (criteria.getType() != null) {
                predicates.add( cb.equal(root.get("type"), criteria.getType()));
            }

            if (!predicates.isEmpty()) {
                query.where(predicates.toArray(new Predicate[predicates.size()]));
            } else {
                query.where(cb.conjunction());
            }

            Expression<Object> statusOrder = cb.selectCase()
                    .when(cb.equal(root.get("status"), UserStatus.ACTIVE), 1)
                    .when(cb.equal(root.get("status"), UserStatus.INACTIVE), 2)
                    .otherwise(3);

            query.orderBy(cb.asc(statusOrder), cb.desc(root.get("created")));

            return query.getRestriction();
        }, pageable);
    }

}