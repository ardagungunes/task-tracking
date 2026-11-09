package com.arda.tasktracking.service;

import com.arda.tasktracking.entity.Authority;
import com.arda.tasktracking.entity.User;
import com.arda.tasktracking.repository.UserRepository;
import com.arda.tasktracking.request.UpdatePasswordRequest;
import com.arda.tasktracking.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo(){

        User user = getAuthenticatedUser();

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName() + " " + user.getLastName(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList()
        );
    }

    @Override
    @Transactional
    public void deleteUser(){
        User user = getAuthenticatedUser();

        if (isLastAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The last admin can not be deleted");
        }

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        User user = getAuthenticatedUser();

        if(!isOldPasswordCorrect(user.getPassword(), updatePasswordRequest.getOldPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }

        if(!isNewPasswordConfirmed(
                updatePasswordRequest.getNewPassword1(),
                updatePasswordRequest.getNewPassword2()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New passwords do not match");
        }

        if(!isNewPasswordDifferent(updatePasswordRequest.getOldPassword(), updatePasswordRequest.getNewPassword1())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from the current password");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword1()));
        this.userRepository.save(user);
    }

    private boolean isOldPasswordCorrect(String currentPassword, String oldPassword){
        return passwordEncoder.matches(oldPassword, currentPassword);
    }

    private boolean isNewPasswordConfirmed(String newPassword, String newPasswordConfirm){
        return newPassword.equals(newPasswordConfirm);
    }

    private boolean isNewPasswordDifferent(String oldPassword, String newPassword){
        return !(oldPassword.equals(newPassword));
    }

    private boolean isLastAdmin(User user){

        boolean isAdmin = user.getAuthorities()
                .stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        if(isAdmin){
            long adminCount = userRepository.countAdmins();

            return adminCount <= 1;
        }

        return false;
    }
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Authentication is required.");
        }

        return (User) authentication.getPrincipal();
} }
