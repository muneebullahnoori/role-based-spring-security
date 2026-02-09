package com.example.exam.dto.user;

import java.util.List;

public class UserCreatDto {
    private String username;
    private String password;
    private String email;
    private List<Long> roleIds;

    public UserCreatDto() {
    }

    public UserCreatDto(String username, String password, String email, List<Long> roleIds) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleIds = roleIds;
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

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
