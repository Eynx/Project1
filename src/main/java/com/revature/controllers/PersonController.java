package com.revature.controllers;

import com.revature.models.Person;
import com.revature.models.Reimbursement;
import com.revature.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class PersonController
{
	private final PersonService personService;

	@Autowired
	public PersonController(PersonService personService)
	{
		this.personService = personService;
	}

	@GetMapping
	public List<Person> getAllUsers()
	{
		return personService.getAllPeople();
	}

	@PostMapping
	public Person addUser(@RequestBody Person person)
	{
		if(personService.addPerson(person)) {
			return person;
		} else {
			return null;
		}
	}

	@GetMapping("/{id}")
	public Person getUser(@PathVariable("id") int id)
	{
		return personService.getPersonById(id);
	}

	@PutMapping("/{id}")
	public Person updateUser(@PathVariable("id") int id, @RequestBody Person person)
	{
		if(person != null) {
			person.setId(id);
			if(personService.updatePerson(person)) {
				return person;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@DeleteMapping("/{id}")
	public boolean removeUser(@PathVariable("id") int id)
	{
		return personService.removePersonById(id);
	}

	@GetMapping("/{id}/reimbursements")
	public List<Reimbursement> getReimbursementsByUserId(@PathVariable("id") int id)
	{
		return personService.getPersonById(id).getReimbursements();
	}

	@PostMapping("/{id}/reimbursements")
	public Person addReimbursement(@PathVariable("id") int id, @RequestBody Reimbursement reimbursement)
	{
		return personService.submitReimbursement(id, reimbursement.getId());
	}
}
