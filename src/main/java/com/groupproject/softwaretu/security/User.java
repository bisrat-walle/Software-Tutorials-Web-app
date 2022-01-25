package com.groupproject.softwaretu.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;

import com.groupproject.softwaretu.tutorial.Tutorial;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.groupproject.softwaretu.enrollement.Enrollement;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true)
    @NotNull
    @Size(min = 5, message  = "username must be at least 5 characters long ")
    private String username;
    @Column(unique = true)
    @NotNull
    @Size(min = 5, message  = "email must be at least 5 characters long ")
    private String email;
    @NotNull
    private String fullName;
    private String password;

    @NotNull
    private String role;

//    @Column(name = "reset_password_token", length = 30)
//    private String resetPasswordToken;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @Column(nullable = true)
    Set<Enrollement> enrollements;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @Column(nullable = true)
    Set<Tutorial> tutorials;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_"+this.role));
    }

     public User getUser() {
        return this;
     }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}