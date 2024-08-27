package ru.geekbrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class DiplomaProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiplomaProjectApplication.class, args);
	}
}
