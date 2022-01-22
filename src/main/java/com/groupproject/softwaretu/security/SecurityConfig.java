package com.groupproject.softwaretu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepo){
        return username -> {
            User user= userRepo.findByUsername(username);
            if (user == null){
                throw new UsernameNotFoundException(
                    "User with username "+username+" doesnot exist"
                );
            }
            return user;
        };
        

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeRequests()
                .antMatchers("/tutorials/enrolled").hasRole("CLIENT")
                .antMatchers("/tutorials/mytutorials").hasRole("INSTRUCTOR")
                .antMatchers("/", "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/tutorials/all")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .build();
    }

}
