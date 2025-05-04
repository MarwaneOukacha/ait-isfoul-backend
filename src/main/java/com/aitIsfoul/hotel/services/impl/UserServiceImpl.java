package com.aitIsfoul.hotel.services.impl;

import com.aitIsfoul.hotel.dao.UserDao;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.SearchUserCriteria;
import com.aitIsfoul.hotel.entity.dto.request.AddUserRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateUserRequest;
import com.aitIsfoul.hotel.entity.dto.response.AddUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.SearchUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateUserResponse;
import com.aitIsfoul.hotel.mapper.UserMapper;
import com.aitIsfoul.hotel.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;

    @Override
    public AddUserResponse addUser(AddUserRequest addUserRequest) {
        log.info("UserServiceImpl :: addUser {}", addUserRequest);
        User user = userDao.addUser(addUserRequest);
        log.info("UserDao add user response inside UserServiceImpl: {}", user);
        return userMapper.userToAddUserResponse(user);
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        log.info("UserServiceImpl :: updateUser {}", updateUserRequest);
        User user = userDao.updateUser(updateUserRequest);
        log.info("UserDao update user response inside UserServiceImpl: {}", user);
        return userMapper.userToUpdateUserResponse(user);
    }

    @Override
    public GenericPage<SearchUserResponse> getAllUsers(SearchUserCriteria criteria, Pageable pageable) {
        log.info("UserServiceImpl :: findAllWithCriteria {}", criteria);
        Page<User> users = userDao.findAllWithCriteria(criteria, pageable);
        Page<SearchUserResponse> searchUserResponses = userMapper.userPageToSearchUserResponsePage(users);

        return new GenericPage<>(searchUserResponses);
    }
}