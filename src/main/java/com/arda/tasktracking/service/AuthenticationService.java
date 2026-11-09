package com.arda.tasktracking.service;

import com.arda.tasktracking.request.AuthenticationRequest;
import com.arda.tasktracking.request.RegisterRequest;
import com.arda.tasktracking.response.AuthenticationResponse;

public interface AuthenticationService {

    void register(RegisterRequest input);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
