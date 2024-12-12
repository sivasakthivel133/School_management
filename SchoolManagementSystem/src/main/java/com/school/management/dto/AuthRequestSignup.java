package com.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestSignup {
    private String name;
    private String email;
    private String password;
    private String roles;
}
