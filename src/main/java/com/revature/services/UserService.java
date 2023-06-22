package com.revature.services;

import com.revature.models.User;
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

	public List<User> getAllPeople()
	{
		return userDao.findAll();
	}

	public User getPersonById(int id) throws UserNotFoundException
	{
		return userDao.findById(id).orElseThrow(() -> new UserNotFoundException("No person found with the id: " + id));
	}

	public boolean addPerson(User user)
	{
		return userDao.save(user).getId() > 0;
	}

	public boolean updatePerson(User user)
	{
		if(user.getId() > 0) {
			userDao.save(user);
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
		User user = userDao.findById(id).orElseThrow(() -> new UserNotFoundException("No person found with the id: " + id));
		return user.getReimbursements();
	}

	public User submitReimbursement(int personId, int reimbursementId) throws UserNotFoundException, ReimbursementNotFoundException
	{
		User user = getPersonById(personId);
		Reimbursement reimbursement = reimbursementDao.findById(reimbursementId).orElseThrow(() -> new ReimbursementNotFoundException("No reimbursement found with the id: " + reimbursementId));

		if(!user.getReimbursements().contains(reimbursement)) {
			user.getReimbursements().add(reimbursement);
			userDao.save(user);
		}

		return user;
	}
}
