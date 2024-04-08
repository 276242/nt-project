package com.example.ntproject.controller.auth.dto;

import com.example.ntproject.commmonTypes.UserRole;

public class RegisterResponseDto {

    private long userId;
    private String username;
    private UserRole role;

    public RegisterResponseDto(String username, UserRole role, long userId) {
        this.username = username;
        this.role = role;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
