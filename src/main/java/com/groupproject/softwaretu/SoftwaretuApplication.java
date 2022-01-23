package com.groupproject.softwaretu;

import com.groupproject.softwaretu.security.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.groupproject.softwaretu.security.UserRepository;

@SpringBootApplication
public class SoftwaretuApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwaretuApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner dataLoader(UserRepository repo) {
//		return args -> {
//			User user = new User();
//			user.setUsername("admin");
//			user.setFullName("admin");
//			user.setRole("ADMIN");
//			user.setPassword("admin");
//			user.setEmail("admin@gmail.com");
//			repo.save(user);
//		};
//	}

}
