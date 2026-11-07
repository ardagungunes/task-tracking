package com.arda.tasktracking.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthenticationRequest {

    @NotBlank(message = "This field is mandatory")
    @Email(message = "Format is invalid")
    private String email;

    @NotBlank(message = "This field is mandatory")
    @Size(min = 5, max = 30)
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
