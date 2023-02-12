package ru.shum.springcourse.Project2Boot.utill;

import org.springframework.stereotype.Component;

import ru.shum.springcourse.Project2Boot.dao.PersonDAO;


@Component
public class PersonValidator {
	
	private final PersonDAO personDAO;
	
	public PersonValidator(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

}
