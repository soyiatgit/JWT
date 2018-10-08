package com.springboot.models;

public class Person {

	private Integer id;
	private String name;
	private Integer age;
	
	public Person() {
		
	}
	
	public Person(String name, Integer age, Integer id) {
		this.id = id;
		this.age = age;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	public void setAge(Integer age) {
		if(age < 0) {
			this.age = 0;
		} else {
			this.age = age;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

}
