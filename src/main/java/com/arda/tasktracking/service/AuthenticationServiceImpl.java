package com.arda.tasktracking.service;

import com.arda.tasktracking.entity.Authority;
import com.arda.tasktracking.entity.User;
import com.arda.tasktracking.repository.UserRepository;
import com.arda.tasktracking.request.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
