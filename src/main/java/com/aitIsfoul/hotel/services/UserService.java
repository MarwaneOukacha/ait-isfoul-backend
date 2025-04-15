package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.SearchUserCriteria;
import com.aitIsfoul.hotel.entity.dto.request.AddUserRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateUserRequest;
import com.aitIsfoul.hotel.entity.dto.response.AddUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.SearchUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateUserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface UserService {
    AddUserResponse addUser(@Valid AddUserRequest addUserRequest);

    UpdateUserResponse updateUser(@Valid UpdateUserRequest updateUserRequest);

    GenericPage<SearchUserResponse> getAllUsers(SearchUserCriteria criteria, Pageable pageable);

}