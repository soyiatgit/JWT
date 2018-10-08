package com.springboot.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.security.AccountCredentials;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
	
	@PostMapping("/")
	public String get(@RequestBody AccountCredentials credentials) {
		System.out.println(credentials.getUsername());
		return "Login success";
	}
}
