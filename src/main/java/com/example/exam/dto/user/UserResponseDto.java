package com.example.exam.dto.user;

import java.util.List;

public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private List<String> roleNames;

    public UserResponseDto() {
    }

    public UserResponseDto(Long id, String username, String password, String email, List<String> roleNames) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleNames = roleNames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
