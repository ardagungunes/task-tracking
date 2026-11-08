package com.arda.tasktracking.service;

import com.arda.tasktracking.entity.User;
import com.arda.tasktracking.response.UserResponse;

import java.nio.file.AccessDeniedException;

public interface UserService {

    UserResponse getUserInfo() throws AccessDeniedException;
    void deleteUser() throws Exception;

}
