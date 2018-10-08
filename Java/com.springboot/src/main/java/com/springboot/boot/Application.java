package com.springboot.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// need to specify the location for your components, services
// repos explicitly if they are present in a different package 
// from this file.
@ComponentScan({"com.springboot"})
@SpringBootApplication
public class Application {

	static int sum = 0;
	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}

}
