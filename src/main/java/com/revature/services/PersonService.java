package com.revature.services;

import com.revature.models.Person;
import com.revature.models.Reimbursement;
import com.revature.daos.PersonDAO;
import com.revature.daos.ReimbursementDAO;
import com.revature.exceptions.PersonNotFoundException;
import com.revature.exceptions.ReimbursementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService
{
	private final PersonDAO personDao;
	private final ReimbursementDAO reimbursementDao;

	@Autowired
	public PersonService(PersonDAO personDao, ReimbursementDAO reimbursementDao)
	{
		this.personDao = personDao;
		this.reimbursementDao = reimbursementDao;
	}

	public List<Person> getAllPeople()
	{
		return personDao.findAll();
	}

	public Person getPersonById(int id) throws PersonNotFoundException
	{
		return personDao.findById(id).orElseThrow(() -> new PersonNotFoundException("No person found with the id: " + id));
	}

	public boolean addPerson(Person person)
	{
		return personDao.save(person).getId() > 0;
	}

	public boolean updatePerson(Person person)
	{
		if(person.getId() > 0)
		{
			personDao.save(person);
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean removePersonById(int id)
	{
		personDao.deleteById(id);
		return !personDao.existsById(id);
	}

	public List<Reimbursement> getReimbursementsByPersonId(int id) throws PersonNotFoundException
	{
		Person person = personDao.findById(id).orElseThrow(() -> new PersonNotFoundException("No person found with the id: " + id));
		return person.getReimbursements();
	}

	public Person submitReimbursement(int personId, int reimbursementId) throws PersonNotFoundException, ReimbursementNotFoundException
	{
		Person person = getPersonById(personId);
		Reimbursement reimbursement = reimbursementDao.findById(reimbursementId).orElseThrow(() -> new ReimbursementNotFoundException("No reimbursement found with the id: " + reimbursementId));

		if(!person.getReimbursements().contains(reimbursement))
		{
			person.getReimbursements().add(reimbursement);
			personDao.save(person);
		}

		return person;
	}
}
