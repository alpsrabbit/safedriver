package com.safedrive.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String createdAt;
    private String updatedAt;
} 