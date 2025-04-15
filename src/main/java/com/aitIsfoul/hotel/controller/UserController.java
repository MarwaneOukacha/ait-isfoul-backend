package com.aitIsfoul.hotel.controller;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.SearchUserCriteria;
import com.aitIsfoul.hotel.entity.dto.request.AddUserRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateUserRequest;
import com.aitIsfoul.hotel.entity.dto.response.AddUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.SearchUserResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateUserResponse;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import com.aitIsfoul.hotel.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<GenericPage<SearchUserResponse>> getUsers(@RequestParam(required = false) String keyword,
                                                                    @RequestParam(required = false) String roleName,
                                                                    @RequestParam(required = false) UserStatus status,
                                                                    @RequestParam(required = false) UserType type
            , Pageable pageable) {
        log.info("Search users request");
        SearchUserCriteria criteria=new SearchUserCriteria(keyword, roleName, status, type);
        GenericPage<SearchUserResponse> users = userService.getAllUsers(criteria, pageable);
        log.info("Search users response {}", users.getPage().getContent());
        return ResponseEntity.ok(users);
    }


    @PostMapping("/add")
    public ResponseEntity<AddUserResponse> addUser(@RequestBody @Valid AddUserRequest addUserRequest) {
        log.info("Add user request: {}", addUserRequest);
        AddUserResponse addUserResponse = userService.addUser(addUserRequest);
        return ResponseEntity.ok(addUserResponse);
    }
    @PatchMapping("/updateUser")
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        log.info("Update user request: {}", updateUserRequest);
        UpdateUserResponse updateUserResponse = userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(updateUserResponse);
    }
}
