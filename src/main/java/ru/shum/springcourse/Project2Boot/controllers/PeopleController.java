package ru.shum.springcourse.Project2Boot.controllers;

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

import jakarta.validation.Valid;
import ru.shum.springcourse.Project2Boot.models.Person;
import ru.shum.springcourse.Project2Boot.services.PeopleService;

@Controller
@RequestMapping("/people")
public class PeopleController {
	
	private final PeopleService peopleService;
	
	@Autowired
	public PeopleController(PeopleService peopleService) {
		this.peopleService = peopleService;
	}
	
	@GetMapping()
	public String people(Model model) {
		model.addAttribute("people", peopleService.findAll());
		return "people/all";
	}
	
	@GetMapping("/new")
	public String newPerson(@ModelAttribute("person") Person person) {
		return "people/new";
	}
	
	@PostMapping()
	public String create(@ModelAttribute("person") @Valid Person person, 
			BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "people/new";
		peopleService.save(person);
		return "redirect:/people";
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") int id) {
		model.addAttribute("person", peopleService.findOne(id));
		
		return "people/edit";
	}
	
	@PatchMapping("/{id}")
	public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, 
			@PathVariable("id") int id) {
		if(bindingResult.hasErrors()) 
			return "people/edit";
		peopleService.update(id, person);
		return "redirect:/people";
	}
	
	@GetMapping("/{id}")
	public String person(Model model, @PathVariable("id") int id) {
		model.addAttribute("person", peopleService.findOne(id));
		model.addAttribute("books", peopleService.findByPersonId(id));	
		
		return "people/id";
	}
	
	@DeleteMapping("/{id}")
	public String del(Model model, @PathVariable("id") int id) {
		peopleService.delete(id);
		return "redirect:/people";
	}
	
}
