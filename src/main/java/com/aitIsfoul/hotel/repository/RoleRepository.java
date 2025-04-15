package com.aitIsfoul.hotel.repository;

import com.aitIsfoul.hotel.entity.Role;
import com.aitIsfoul.hotel.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(RoleType roleName);

}