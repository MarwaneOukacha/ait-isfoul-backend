package com.aitIsfoul.hotel.dao;

import com.aitIsfoul.hotel.entity.dto.request.AddUserRequest;
import com.aitIsfoul.hotel.entity.dto.SearchUserCriteria;
import com.aitIsfoul.hotel.entity.dto.request.UpdateUserRequest;
import com.aitIsfoul.hotel.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserDao {

    User addUser(AddUserRequest addUserRequest);

    User updateUser(UpdateUserRequest updateUserRequest);
    User getUserByIdentifier(String identifier);

    Page<User> findAllWithCriteria(SearchUserCriteria criteria, Pageable pageable);

    User getUserById(String ownerId);
    Optional<User> getUserByEmail(String email);

    void save(User user);

}