package com.groupproject.softwaretu.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    private String role;

    User toUser(PasswordEncoder encoder) {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(encoder.encode(this.password));
        user.setFullName(this.fullName);
        user.setEmail(this.email);
        user.setRole(this.role);
        return user;
    }

    User myToUser(User user, PasswordEncoder encoder) {
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }
}
