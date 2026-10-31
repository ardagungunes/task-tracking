package com.arda.tasktracking.service;

import com.arda.tasktracking.request.RegisterRequest;

public interface AuthenticationService {

    void register(RegisterRequest input) throws Exception;
}
