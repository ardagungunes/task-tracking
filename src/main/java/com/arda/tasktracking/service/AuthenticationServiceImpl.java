package com.arda.tasktracking.service;

import com.arda.tasktracking.entity.Authority;
import com.arda.tasktracking.entity.User;
import com.arda.tasktracking.repository.UserRepository;
import com.arda.tasktracking.request.AuthenticationRequest;
import com.arda.tasktracking.request.RegisterRequest;
import com.arda.tasktracking.response.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void register(RegisterRequest input) throws Exception {

        if(isEmailExist(input.getEmail())){
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = buildNewUser(input);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword())
        );

        User user = this.userRepository.findByEmail(authenticationRequest.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String jwtToken = this.jwtService.generateToken(new HashMap<>(), user);

        return new AuthenticationResponse(jwtToken);
    }

    private boolean isEmailExist(String email){
        return userRepository.findByEmail(email).isPresent();
    }


    private User buildNewUser(RegisterRequest input) {
        User user = new User();

        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setAuthorities(initialAuthority());
        return user;
    }

    private List<Authority> initialAuthority(){
        boolean isFirstUser = userRepository.count() == 0;

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_USER"));

        if (isFirstUser) {
            authorities.add(new Authority("ROLE_ADMIN"));
        }
        return authorities;


    }
}
