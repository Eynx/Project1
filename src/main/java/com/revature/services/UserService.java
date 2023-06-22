package com.revature.services;

import com.revature.models.Person;
import com.revature.models.Reimbursement;
import com.revature.daos.UserDAO;
import com.revature.daos.ReimbursementDAO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.ReimbursementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
	private final UserDAO userDao;
	private final ReimbursementDAO reimbursementDao;

	@Autowired
	public UserService(UserDAO userDao, ReimbursementDAO reimbursementDao)
	{
		this.userDao = userDao;
		this.reimbursementDao = reimbursementDao;
	}

	public List<Person> getAllPeople()
	{
		return userDao.findAll();
	}

	public Person getPersonById(int id) throws UserNotFoundException
	{
		return userDao.findById(id).orElseThrow(() -> new UserNotFoundException("No person found with the id: " + id));
	}

	public boolean addPerson(Person person)
	{
		return userDao.save(person).getId() > 0;
	}

	public boolean updatePerson(Person person)
	{
		if(person.getId() > 0) {
			userDao.save(person);
			return true;
		} else {
			return false;
		}
	}

	public boolean removePersonById(int id)
	{
		userDao.deleteById(id);
		return !userDao.existsById(id);
	}

	public List<Reimbursement> getReimbursementsByPersonId(int id) throws UserNotFoundException
	{
		Person person = userDao.findById(id).orElseThrow(() -> new UserNotFoundException("No person found with the id: " + id));
		return person.getReimbursements();
	}

	public Person submitReimbursement(int personId, int reimbursementId) throws UserNotFoundException, ReimbursementNotFoundException
	{
		Person person = getPersonById(personId);
		Reimbursement reimbursement = reimbursementDao.findById(reimbursementId).orElseThrow(() -> new ReimbursementNotFoundException("No reimbursement found with the id: " + reimbursementId));

		if(!person.getReimbursements().contains(reimbursement)) {
			person.getReimbursements().add(reimbursement);
			userDao.save(person);
		}

		return person;
	}
}
