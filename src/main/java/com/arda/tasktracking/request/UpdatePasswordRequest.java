package com.arda.tasktracking.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequest {

    @NotBlank(message = "This field is mandatory")
    @Size(min = 5, max = 30)
    private String oldPassword;

    @NotBlank(message = "This field is mandatory")
    @Size(min = 5, max = 30)
    private String newPassword1;

    @NotBlank(message = "This field is mandatory")
    @Size(min = 5, max = 30)
    private String newPassword2;

    public UpdatePasswordRequest(String oldPassword, String newPassword1, String newPassword2) {
        this.oldPassword = oldPassword;
        this.newPassword1 = newPassword1;
        this.newPassword2 = newPassword2;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }
}
