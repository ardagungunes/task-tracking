package com.arda.tasktracking.service;

import com.arda.tasktracking.entity.User;
import com.arda.tasktracking.response.UserResponse;


public interface UserService {

    UserResponse getUserInfo();
    void deleteUser();

}
