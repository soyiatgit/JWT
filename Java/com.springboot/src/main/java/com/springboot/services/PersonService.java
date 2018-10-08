package com.springboot.services;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.springboot.models.Person;
import com.springboot.repositories.PersonRepo;

@Service
public class PersonService {
	
	public List<Person> getPersonList() {
		return PersonRepo.getPersonList();
	}
	
	public Person getPerson() {
		Random rand = new Random();
		// Random.nextInt(n) gives a random integer between 0 to n-1.
		// since we have 14 person, we need a number between 0 to 13.
		int index = rand.nextInt(14);
		return PersonRepo.getPersonList().get(index);
	}

	public Person getPersonById(Integer id) {
		 return PersonRepo.getPersonList().stream()
				.filter(p -> p.getId().equals(id))
				.peek(System.out::println)
				.findAny()
				.orElse(null);
	}
}
