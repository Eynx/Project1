package com.revature.controllers;

import com.revature.models.User;
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
	public List<User> getAllUsers()
	{
		return userService.getAllPeople();
	}

	@PostMapping
	public User addUser(@RequestBody User user)
	{
		if(userService.addPerson(user)) {
			return user;
		} else {
			return null;
		}
	}

	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") int id)
	{
		return userService.getPersonById(id);
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable("id") int id, @RequestBody User user)
	{
		if(user != null) {
			user.setId(id);
			if(userService.updatePerson(user)) {
				return user;
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
		return userService.getPersonById(id).getReimbursements();
	}

	@PostMapping("/{id}/reimbursements")
	public User addReimbursement(@PathVariable("id") int id, @RequestBody Reimbursement reimbursement)
	{
		return userService.submitReimbursement(id, reimbursement.getId());
	}
}
