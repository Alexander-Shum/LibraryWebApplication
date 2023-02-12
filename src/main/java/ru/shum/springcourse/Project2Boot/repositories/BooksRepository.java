package ru.shum.springcourse.Project2Boot.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.shum.springcourse.Project2Boot.models.Book;


@Repository
public interface BooksRepository extends JpaRepository<Book, Integer>{
	
	List<Book> findByNameStartingWith(String title);
}
