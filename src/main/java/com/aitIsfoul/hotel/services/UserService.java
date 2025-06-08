package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.SearchUserCriteria;
import com.aitIsfoul.hotel.entity.dto.request.AddUserRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateUserRequest;
import com.aitIsfoul.hotel.entity.dto.response.AddUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.SearchUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateUserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    AddUserResponse addUser(@Valid AddUserRequest addUserRequest);

    UpdateUserResponse updateUser(@Valid UpdateUserRequest updateUserRequest);
    Optional<User> getUserByEmail(String email);
    void save(User user);
    GenericPage<SearchUserResponse> getAllUsers(SearchUserCriteria criteria, Pageable pageable);

}