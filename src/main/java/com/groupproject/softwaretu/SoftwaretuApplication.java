package com.groupproject.softwaretu;

import java.beans.Encoder;

import com.groupproject.softwaretu.security.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.groupproject.softwaretu.security.UserRepository;

@SpringBootApplication
public class SoftwaretuApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwaretuApplication.class, args);
	}

	@Autowired
	private PasswordEncoder encoder;

	@Bean
	public CommandLineRunner dataLoader(UserRepository repo) {
		return args -> {

			User temp = repo.findByUsername("admin");
			if (temp == null){
				User user = new User();
				user.setUsername("admin");
				user.setFullName("admin");
				user.setRole("ADMIN");
				user.setPassword(encoder.encode("admin"));
				user.setEmail("admin@gmail.com");
				repo.save(user);
			}

		};
	}

}
