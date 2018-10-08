package com.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.models.Person;
import com.springboot.services.PersonService;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {
	
	@Autowired
	PersonService personService;
	
	@GetMapping("/")
	public Person get() {
		return personService.getPerson();
	}
	
	@GetMapping("/{id}")
	public Person getById(@PathVariable("id") Integer id) {
		return personService.getPersonById(id);
	}
	
	@GetMapping("/list")
	public List<Person> getPersonList() {
		return personService.getPersonList();
	}
}
