package com.revature.services;

import com.revature.daos.StatusDAO;
import com.revature.dtos.ReimbursementDTO;
import com.revature.exceptions.StatusNotFoundException;
import com.revature.models.Person;
import com.revature.models.Reimbursement;
import com.revature.daos.UserDAO;
import com.revature.daos.ReimbursementDAO;
import com.revature.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
	private final UserDAO userDao;
	private final ReimbursementDAO reimbursementDao;
	private final StatusDAO statusDAO;

	@Autowired
	public UserService(UserDAO userDao, ReimbursementDAO reimbursementDao, StatusDAO statusDAO)
	{
		this.userDao = userDao;
		this.reimbursementDao = reimbursementDao;
		this.statusDAO = statusDAO;
	}

	public List<Person> getAllPeople()
	{
		return userDao.findAll(Sort.by(Sort.Direction.ASC, "id"));
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

	public Person submitReimbursement(int personId, ReimbursementDTO reimbursementDTO) throws UserNotFoundException, StatusNotFoundException
	{
		Person person = getPersonById(personId);

		Reimbursement reimbursement = new Reimbursement();
		reimbursement.setAmount(reimbursementDTO.getAmount());
		reimbursement.setDescription(reimbursementDTO.getDescription());
		reimbursement.setUser(person);
		reimbursement.setStatus(statusDAO.findByName("Pending").orElseThrow());
		reimbursementDao.save(reimbursement);

		person.getReimbursements().add(reimbursement);
		userDao.save(person);

		return person;
	}
}
