package com.groupproject.softwaretu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    UserRepository userRepository;

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
            .antMatchers("/tutorials/enrolled").hasAnyRole("ADMIN", "CLIENT")
            .antMatchers("/tutorials/mytutorials").hasRole("INSTRUCTOR")
            .antMatchers("/tutorials/create").hasRole("INSTRUCTOR")
            .antMatchers("/tutorials/edit").hasRole("INSTRUCTOR")
            .antMatchers("/tutorials/all").hasAnyRole("ADMIN", "INSTRUCTOR", "CLIENT")
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/manage/tutorials").hasRole("ADMIN")
            .antMatchers("/manage/instructors").hasRole("ADMIN")
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/profile").hasAnyRole("ADMIN", "INSTRUCTOR", "CLIENT")
            .antMatchers("/profile/edit").hasAnyRole("ADMIN", "INSTRUCTOR", "CLIENT")
            .antMatchers("/", "/**").permitAll()
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/login/redirect")
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .build();
    }

}
