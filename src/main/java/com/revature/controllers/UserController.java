package com.revature.controllers;

import com.revature.dtos.ReimbursementDTO;
import com.revature.models.Person;
import com.revature.models.Reimbursement;
import com.revature.services.UserService;
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
public class UserController
{
	private final UserService userService;

	@Autowired
	public UserController(UserService userService)
	{
		this.userService = userService;
	}

	@GetMapping
	public List<Person> getAllUsers()
	{
		return userService.getAllPeople();
	}

	@PostMapping
	public Person addUser(@RequestBody Person person)
	{
		if(userService.addPerson(person)) {
			return person;
		} else {
			return null;
		}
	}

	@GetMapping("/{id}")
	public Person getUser(@PathVariable("id") int id)
	{
		return userService.getPersonById(id);
	}

	@PutMapping("/{id}")
	public Person updateUser(@PathVariable("id") int id, @RequestBody Person person)
	{
		if(person != null) {
			person.setId(id);
			if(userService.updatePerson(person)) {
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
		return userService.removePersonById(id);
	}

	@GetMapping("/{id}/reimbursements")
	public List<Reimbursement> getReimbursementsByUserId(@PathVariable("id") int id)
	{
		return userService.getReimbursementsByPersonId(id);
	}

	@PostMapping("/{id}/reimbursements")
	public Person addReimbursement(@PathVariable("id") int id, @RequestBody ReimbursementDTO reimbursementDTO)
	{
		return userService.submitReimbursement(id, reimbursementDTO);
	}
}
