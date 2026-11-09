package com.arda.tasktracking.controller;

import com.arda.tasktracking.entity.User;
import com.arda.tasktracking.response.UserResponse;
import com.arda.tasktracking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public UserResponse getUserInfo(){
        return this.userService.getUserInfo();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    public void deleteUser(){
        userService.deleteUser();
    }
}
