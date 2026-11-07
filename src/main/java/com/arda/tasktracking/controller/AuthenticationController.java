package com.arda.tasktracking.controller;

import com.arda.tasktracking.request.AuthenticationRequest;
import com.arda.tasktracking.request.RegisterRequest;
import com.arda.tasktracking.response.AuthenticationResponse;
import com.arda.tasktracking.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) throws Exception{
        authenticationService.register(registerRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        return authenticationService.login(authenticationRequest);
    }
}
