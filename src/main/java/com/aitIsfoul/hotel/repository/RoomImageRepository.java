package com.aitIsfoul.hotel.repository;

import com.aitIsfoul.hotel.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, UUID> {
}
