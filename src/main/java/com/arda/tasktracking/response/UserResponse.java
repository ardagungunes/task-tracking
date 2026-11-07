package com.arda.tasktracking.response;

import com.arda.tasktracking.entity.Authority;
import com.arda.tasktracking.entity.User;

import java.util.List;

public class UserResponse {

    private Long id;
    private String email;
    private String fullName;
    private List<Authority> authorityList;


    public UserResponse(Long id, String email, String fullName, List<Authority> authorityList) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.authorityList = authorityList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }

}
