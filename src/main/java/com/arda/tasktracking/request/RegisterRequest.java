package com.arda.tasktracking.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "This field is mandatory")
    @Size(min = 1, max = 30)
    private String firstName;

    @NotBlank(message = "This field is mandatory")
    @Size(min = 1, max = 30)
    private String lastName;

    @NotBlank(message = "This field is mandatory")
    @Email(message = "Format is invalid")
    private String email;


    @NotBlank(message = "This field is mandatory")
    @Size(min = 5, max = 30)
    private String password;

    public RegisterRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
