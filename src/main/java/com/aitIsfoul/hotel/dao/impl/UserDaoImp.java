package com.aitIsfoul.hotel.dao.impl;

import com.aitIsfoul.hotel.dao.UserDao;
import com.aitIsfoul.hotel.entity.Role;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.entity.dto.SearchUserCriteria;
import com.aitIsfoul.hotel.entity.dto.request.AddUserRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateUserRequest;
import com.aitIsfoul.hotel.enums.RoleType;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.mapper.UserMapper;
import com.aitIsfoul.hotel.repository.RoleRepository;
import com.aitIsfoul.hotel.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserDaoImp implements UserDao {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder encoder;


    @Override
    public User addUser(AddUserRequest addUserRequest) {
        log.info("userDao :: addUser {}", addUserRequest);
        User user = userMapper.addUserToUser(addUserRequest);
        Role userRole=roleRepository.findByRoleName(RoleType.USER).get();
        if(userRole!=null){
            user.setRole(userRole);
        }else {
            log.error("userDao :: addUser userRole is null");
            throw new EntityNotFoundException("Role not found");
        }

        user.setIsActive("Y");
        user.setPassword(encoder.encode(addUserRequest.getPassword()));
        user.setStatus(UserStatus.INACTIVE);
        return userRepository.save(user);
    }
    @Override
    public User updateUser(UpdateUserRequest updateUserRequest) {
        log.info("userDao :: updateUser {}", updateUserRequest);
        User user = userRepository.findById(UUID.fromString(updateUserRequest.getId()))
                .orElseThrow(
                        () -> new EntityNotFoundException("User not found with ID: " + updateUserRequest.getIden()));
        if(updateUserRequest.getFirstName() != null && !updateUserRequest.getFirstName().isEmpty()) {
            user.setFirstName(updateUserRequest.getFirstName());
            log.info("userDao :: updateUser firstName {}", user);
        }
        if (updateUserRequest.getLastName() != null && !updateUserRequest.getLastName().isEmpty()) {
            user.setLastName(updateUserRequest.getLastName());
            log.info("userDao :: updateUser lastName {}", user);
        }
        if (updateUserRequest.getPhoneNumber() != null && !updateUserRequest.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
            log.info("userDao :: updateUser phoneNumber {}", user);
        }
        if (updateUserRequest.getStatus() != null) {
            user.setStatus(updateUserRequest.getStatus());
            log.info("userDao :: updateUser status {}", user);
        }
        if (updateUserRequest.getIsActive() != null && !updateUserRequest.getIsActive().isEmpty()) {
            user.setIsActive(updateUserRequest.getIsActive());
            log.info("userDao :: updateUser isActive {}", user);
        }
        if (updateUserRequest.getIden() != null && !updateUserRequest.getIden().isEmpty()) {
            user.setIden(updateUserRequest.getIden());
            log.info("userDao :: updateUser identifier {}", user);
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserByIdentifier(String identifier) {
        return userRepository.getUserByIden(identifier).orElseThrow(() -> new EntityNotFoundException("User not found with identifier: " + identifier));
    }

    @Override
    public Page<User> findAllWithCriteria(SearchUserCriteria criteria, Pageable pageable) {
        log.info("UserDaoImpl :: findAllWithCriteria {}", criteria);
        return userRepository.findAllWithCriteria(criteria, pageable);
    }

    @Override
    public User getUserById(String ownerId) {
        return userRepository.findById(UUID.fromString(ownerId)).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + ownerId)
        );
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}