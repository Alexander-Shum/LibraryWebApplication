package ru.shum.springcourse.Project2Boot.controllers;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import ru.shum.springcourse.Project2Boot.models.Book;
import ru.shum.springcourse.Project2Boot.models.Person;
import ru.shum.springcourse.Project2Boot.services.BookService;
import ru.shum.springcourse.Project2Boot.services.PeopleService;

@Controller
@RequestMapping("/books")
public class BooksController {
	
	private final BookService bookService;
	private final PeopleService peopleService;
	
	@Autowired
	public BooksController(BookService bookService, PeopleService peopleService) {
		this.bookService = bookService;
		this.peopleService = peopleService;
	}
	
	@GetMapping()
	public String books(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("books_per_page") Optional<Integer> books_per_page,
			@RequestParam("sort") Optional<Boolean> sort) {
		
		if(!page.isPresent() && !books_per_page.isPresent() && !sort.isPresent()) {
			model.addAttribute("books", bookService.findAll());
		}
		
		if(sort.isPresent() && !page.isPresent() && !books_per_page.isPresent()){
			if(sort.get() == true) {
				model.addAttribute("books", bookService.findAllWithSortByYear());
			}else {
				model.addAttribute("books", bookService.findAll());
			}
		}
		
		if(page.isPresent() && books_per_page.isPresent()){
			model.addAttribute("books", bookService.findAllWithPages(page.get(), books_per_page.get()));
		}
		
		if(page.isPresent() && books_per_page.isPresent() && sort.isPresent()) {
			model.addAttribute("books", bookService.findAllPagesAndSort(page.get(), books_per_page.get()));
		}
	
		return "books/allBooks";
	}
	
	@GetMapping("/newBook")
	public String newBook(@ModelAttribute("book")  Book book) {
		return "books/newBook";
	}
	
	@PostMapping
	public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "books/newBook";
		bookService.save(book);
		return "redirect:/books";
	}
	
	@GetMapping("/{id}/editBook")
	public String updateBook(Model model, @PathVariable("id") int id) {
		model.addAttribute("book", bookService.findOne(id));
		return "books/editBook";
	}
	
	@PatchMapping("/{id}")
	public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id ) {
		if(bindingResult.hasErrors())
			return "books/editBook";
		bookService.update(id, book);
		return "redirect:/books";
	}
	
	@GetMapping("/{id}")
	public String book(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person) {
		model.addAttribute("book", bookService.findOne(id));
		
		Person owner = bookService.findOne(id).getOwner();
		if(owner != null)
			model.addAttribute("owner", owner);
		else
			model.addAttribute("people", peopleService.findAll());
		
		return "books/idBook";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		bookService.delete(id);
		return "redirect:/books";
	}
	
	@PatchMapping("/{id}/free")
	public String free(@PathVariable("id") int id) {
		bookService.free(id);
	return "redirect:/books/" + id;
	}
	
	@PatchMapping("/{id}/add")
	public String add(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
		//bookDAO.add(person.getPersonId(), id);
		
		bookService.add(id, person);
		return "redirect:/books/" + id;
	}
	
	@GetMapping("/search")
	public String search(@RequestParam(value = "searchText", required = false) String text, Model model) {
		if(!bookService.findByNameStart(text).isEmpty()) {
			Book foundBook = bookService.findByNameStart(text).get(0);
			model.addAttribute("book", foundBook );
			if(foundBook.getOwner() != null)
				model.addAttribute("owner", foundBook.getOwner());
			else
				model.addAttribute("nobody", "Книга свободна");
		}else if(text != null){
			model.addAttribute("nothing", "Книг не найдено");
		}
		
		return "books/search";
	}
	
	
}
