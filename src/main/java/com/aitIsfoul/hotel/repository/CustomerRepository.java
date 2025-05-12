package com.aitIsfoul.hotel.repository;
import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.dto.request.SearchCustomerRequest;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> , JpaSpecificationExecutor<Customer> {


    Optional<Customer> findByIden(String iden);
    Optional<Customer> findByEmail(String email);
    default Page<Customer> findAllWithCriteria(SearchCustomerRequest criteria, Pageable pageable) {
        return findAll((Specification<Customer>) (root, query, cb) -> {
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


            if (!predicates.isEmpty()) {
                query.where(predicates.toArray(new Predicate[predicates.size()]));
            } else {
                query.where(cb.conjunction());
            }


            query.orderBy(cb.desc(root.get("created")));

            return query.getRestriction();
        }, pageable);
    }
}