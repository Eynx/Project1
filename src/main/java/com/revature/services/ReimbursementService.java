package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.exceptions.ReimbursementNotFoundException;
import com.revature.models.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

import java.util.List;

public class ReimbursementService
{
	private final ReimbursementDAO reimbursementDAO;

	@Autowired
	public ReimbursementService(ReimbursementDAO reimbursementDAO)
	{
		this.reimbursementDAO = reimbursementDAO;
	}

	public List<Reimbursement> getAllReimbursements()
	{
		return reimbursementDAO.findAll();
	}

	public Reimbursement getReimbursementById(int id)
	{
		return reimbursementDAO.findById(id).orElseThrow(() -> new ReimbursementNotFoundException("No reimbursement found with the id: " + id));
	}

	public boolean addReimbursement(Reimbursement reimbursement)
	{
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
