package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.daos.StatusDAO;
import com.revature.exceptions.ReimbursementNotFoundException;
import com.revature.exceptions.StatusNotFoundException;
import com.revature.models.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReimbursementService
{
	private final ReimbursementDAO reimbursementDAO;
	private final StatusDAO statusDAO;

	@Autowired
	public ReimbursementService(ReimbursementDAO reimbursementDAO, StatusDAO statusDAO)
	{
		this.reimbursementDAO = reimbursementDAO;
		this.statusDAO = statusDAO;
	}

	public List<Reimbursement> getAllReimbursements()
	{
		return reimbursementDAO.findAll();
	}

	public Reimbursement getReimbursementById(int id) throws ReimbursementNotFoundException
	{
		return reimbursementDAO.findById(id).orElseThrow(() -> new ReimbursementNotFoundException("No reimbursement found with the id: " + id));
	}

	public boolean addReimbursement(Reimbursement reimbursement)
	{
		reimbursement.setStatus(statusDAO.findByName("Pending").orElseThrow(() -> new StatusNotFoundException("There was an error finding the default status to assign to the reimbursement.")));
		return reimbursementDAO.save(reimbursement).getId() > 0;
	}

	public boolean updateReimbursement(Reimbursement reimbursement)
	{
		if(reimbursement.getId() > 0)
		{
			reimbursementDAO.save(reimbursement);
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean removeReimbursementById(int id)
	{
		reimbursementDAO.deleteById(id);
		return !reimbursementDAO.existsById(id);
	}
}
