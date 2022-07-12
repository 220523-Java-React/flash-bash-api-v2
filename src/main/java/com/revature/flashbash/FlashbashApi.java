package com.revature.flashbash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class FlashbashApi {
	public static void main(String[] args) {
		SpringApplication.run(FlashbashApi.class, args);
	}
}
