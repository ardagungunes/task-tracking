package com.arda.tasktracking.controller;

import com.arda.tasktracking.entity.User;
import com.arda.tasktracking.response.UserResponse;
import com.arda.tasktracking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public UserResponse getUserInfo() throws AccessDeniedException {
        return this.userService.getUserInfo();
    }

    @DeleteMapping()
    public void deleteUser() throws Exception {
        userService.deleteUser();
    }
}
