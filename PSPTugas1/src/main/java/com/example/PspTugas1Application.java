package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value="com.example")
public class PspTugas1Application {

	public static void main(String[] args) {
		SpringApplication.run(PspTugas1Application.class, args);
	}
}
