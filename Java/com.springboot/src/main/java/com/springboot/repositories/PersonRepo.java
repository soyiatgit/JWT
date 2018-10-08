package com.springboot.repositories;

import java.util.ArrayList;
import java.util.List;

import com.springboot.models.Person;

public class PersonRepo {
	
	public static List<Person> personList;
	
	public static List<Person> getPersonList() {
		if(null == personList || personList.size() < 1)  {
			personList = new ArrayList<Person>();
			personList.add(new Person("Saurabh", 26, 101));
			personList.add(new Person("Harshit", 27, 102));
			personList.add(new Person("Vijay", 27, 103));
			personList.add(new Person("Digvijay", 27, 104));
			personList.add(new Person("Rahul", 30, 105));
			personList.add(new Person("Vinod", 26, 106));
			personList.add(new Person("Nigel", 27, 107));
			personList.add(new Person("Shruti", 25, 108));
			personList.add(new Person("Kamal", 32, 109));
			personList.add(new Person("RajShekhar", 27, 110));
			personList.add(new Person("Komal", 27, 111));
			personList.add(new Person("Rajan", 28, 112));
			personList.add(new Person("Ankita", 27, 113));
			personList.add(new Person("Rajat", 36, 114));
		}
		return personList;
	}

}
