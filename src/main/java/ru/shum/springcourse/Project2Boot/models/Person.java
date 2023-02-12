package ru.shum.springcourse.Project2Boot.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "Person")
public class Person {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message="Имя не должно быть пустым")
	@Size(min = 2, max = 30, message="Имя должно быть длиной от 2 до 30 символов")
	@Column(name = "first_name")
	private String firstName;
	
	@NotEmpty(message="Отчество не должно быть пустым")
	@Size(min = 2, max = 30, message="Отчество должно быть длиной от 2 до 30 символов")
	@Column(name = "middle_name")
	private String middleName;
	
	@NotEmpty(message="Фамилия не должна быть пустой")
	@Size(min = 2, max = 30, message="Фамилия должна быть длиной от 2 до 30 символов")
	@Column(name = "last_name")
	private String lastName;
	
	@Min(value=0, message="Возраст должен быть больше 0")
	@Column(name = "year_born")
	private int yearBorn;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
	private List<Book> books;
	
	public Person(String firstName, String middleName, String lastName, int year) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.yearBorn = year;
	}
	
	public Person() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getYearBorn() {
		return yearBorn;
	}
	public void setYearBorn(int year) {
		this.yearBorn = year;
	}

	public Person(int id, String firstName, String middleName, String lastName, int yearBorn, List<Book> books) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.yearBorn = yearBorn;
		this.books = books;
	}

}
