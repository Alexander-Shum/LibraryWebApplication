package ru.shum.springcourse.Project2Boot.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ru.shum.springcourse.Project2Boot.models.Book;
import ru.shum.springcourse.Project2Boot.models.Person;
import ru.shum.springcourse.Project2Boot.repositories.BooksRepository;
import ru.shum.springcourse.Project2Boot.repositories.PeopleRepository;


//CRUD Methods
@Service
@Transactional
public class BookService {
	
	private final BooksRepository booksRepository;
	private final PeopleRepository peopleRepository;
	
	@Autowired
	public BookService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
		this.booksRepository = booksRepository;
		this.peopleRepository = peopleRepository;
	}
	
	public List<Book> findAll(){
		return booksRepository.findAll();
	}
	
	
	public Book findOne(int id) {
		Optional<Book> foundBook = booksRepository.findById(id);
		return foundBook.orElse(null);
	}
	
	public void save(Book book) {
		booksRepository.save(book);
	}
	
	public void update(int id, Book updatedBook) {
		updatedBook.setId(id);
		booksRepository.save(updatedBook);
	}
	
	public void delete(int id) {
		booksRepository.deleteById(id);
	}
	
	//Bad solution
	//add owner
	public void add(int id, Person person) {
		booksRepository.getOne(id).setOwner(person);
		booksRepository.getOne(id).setDate(new Timestamp(System.currentTimeMillis()));
    }
	
	//Bad solution
	//make book free
	public void free(int id) {
		booksRepository.getOne(id).setOwner(null);
		booksRepository.getOne(id).setDate(null);
	}
	
	public List<Book> findAllWithPages(int page, int per) {
		return booksRepository.findAll(PageRequest.of(page, per)).getContent();
	}
	
	public List<Book> findAllWithSortByYear(){
		return booksRepository.findAll(Sort.by("year"));
	}
	
	public List<Book> findAllPagesAndSort(int page, int per){
		return booksRepository.findAll(PageRequest.of(page, per, Sort.by("year"))).getContent();
	}
	
	public List<Book> findByNameStart(String name) {
		return booksRepository.findByNameStartingWith(name);
	}
}
