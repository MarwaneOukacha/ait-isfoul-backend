package com.aitIsfoul.hotel.mapper;

import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.request.AddUserRequest;
import com.aitIsfoul.hotel.entity.dto.response.AddUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.SearchUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role.roleName", target = "role")
    SearchUserResponse userToSearchUserResponse(User user);
    UpdateUserResponse userToUpdateUserResponse(User user);
    User addUserToUser(AddUserRequest addUserRequest);
    AddUserResponse userToAddUserResponse(User user);

    default Page<SearchUserResponse> userPageToSearchUserResponsePage(Page<User> users) {
        return users.map(this::userToSearchUserResponse);
    }



}