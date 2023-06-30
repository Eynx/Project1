package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.daos.StatusDAO;
import com.revature.exceptions.ReimbursementNotFoundException;
import com.revature.exceptions.StatusNotFoundException;
import com.revature.models.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
		return reimbursementDAO.findAll(Sort.by(Sort.Direction.DESC, "id"));
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

	public boolean approveReimbursementByID(int id)
	{
		Optional<Reimbursement> reimbursement = reimbursementDAO.findById(id);
		if(reimbursement.isPresent()) {
			reimbursement.get().setStatus(statusDAO.findByName("Approved").orElseThrow(() -> new StatusNotFoundException("There was an error finding the default status to assign to the reimbursement.")));
			reimbursementDAO.save(reimbursement.get());
			return true;
		} else {
			return false;
		}
	}

	public boolean denyReimbursementByID(int id)
	{
		Optional<Reimbursement> reimbursement = reimbursementDAO.findById(id);
		if(reimbursement.isPresent()) {
			reimbursement.get().setStatus(statusDAO.findByName("Denied").orElseThrow(() -> new StatusNotFoundException("There was an error finding the default status to assign to the reimbursement.")));
			reimbursementDAO.save(reimbursement.get());
			return true;
		} else {
			return false;
		}
	}
}
