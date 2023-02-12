package ru.shum.springcourse.Project2Boot.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotEmpty(message="Название не должно быть пустым")
	@Size(min=2, max = 100, message="Название должно быть длиной от 2 до 100 символов")
	@Column(name = "name")
	private String name;
	
	@NotEmpty(message="Автор не должен быть пустым")
	@Size(min=2, max = 30, message="Имя должно быть длиной от 2 до 30 символов")
	@Column(name = "author")
	private String author;
	
	@Min(value=0, message="Год должен быть больше 0")
	@Max(value=2022, message="Год не должен быть больше 2022")
	@Column(name = "year")
	private int year;
	
	@Column(name = "date")
	private Date date;
	
	
	@Transient
	private boolean isExpired = false;
	
	@ManyToOne
	@JoinColumn(name = "person_id" , referencedColumnName = "id")
	private Person owner;
	
	public Book(String name, String author, int year) {
		super();
		this.name = name;
		this.author = author;
		this.year = year;
	}

	//for spring
	public Book() {}
	
	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Book(int id, String name, String author, int year, Person owner) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.year = year;
		this.owner = owner;
	}
}
