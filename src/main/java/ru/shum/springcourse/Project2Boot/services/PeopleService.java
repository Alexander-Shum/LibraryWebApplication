package ru.shum.springcourse.Project2Boot.services;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ru.shum.springcourse.Project2Boot.models.Book;
import ru.shum.springcourse.Project2Boot.models.Person;
import ru.shum.springcourse.Project2Boot.repositories.BooksRepository;
import ru.shum.springcourse.Project2Boot.repositories.PeopleRepository;


//CRUD Methods
@Service
@Transactional
public class PeopleService {
	
	private final PeopleRepository peopleRepository;
	
	@Autowired
	public PeopleService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}
	
	public List<Person> findAll(){
		return peopleRepository.findAll();
	}
	
	public Person findOne(int id) {
		Optional<Person> foundPerson = peopleRepository.findById(id);
		return foundPerson.orElse(null);
	}
	
	public void save(Person person) {
		peopleRepository.save(person);
	}
	
	public void update(int id, Person updatedPerson) {
		updatedPerson.setId(id);
		peopleRepository.save(updatedPerson);
	}
	
	public void delete(int id) {
		peopleRepository.deleteById(id);
	}
	
	public List<Book> booksPerson(int id) {
		return peopleRepository.getOne(id).getBooks();
	}
	
	//check decay books for person
	public List<Book> findByPersonId(int id){
		Optional<Person> person = peopleRepository.findById(id);
		
		if(person.isPresent()) {
				
				person.get().getBooks().forEach(book -> {
					if(book.getDate() != null && new Timestamp(System.currentTimeMillis()).getTime() - book.getDate().getTime() > 864000000)
						book.setExpired(true);
				});
				return person.get().getBooks();
		}else {
			return Collections.emptyList();
		}
	}
}
