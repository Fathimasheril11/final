package com.example.BookingApp;

import com.example.BookingApp.entity.UserEntity;
import com.example.BookingApp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingAppApplication.class, args);
	}

	CommandLineRunner init (UserService userService) {
		return args -> {
			try {
				userService.loadUserByUsername("admin");
			} catch (Exception e) {
				UserEntity admin = new UserEntity();
				admin.setUsername("admin");
				admin.setPassword("admin");
				admin.setEmail("admin@gmail.com");
				userService.register(admin);
				System.out.println("Admin created: Username 'admin', Password 'admin'");
			}
		};
	}

}
