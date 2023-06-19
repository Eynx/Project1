package com.revature.controllers;

import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController
{
	private final ReimbursementService reimbursementService;

	@Autowired
	public ReimbursementController(ReimbursementService reimbursementService)
	{
		this.reimbursementService = reimbursementService;
	}

	@GetMapping
	public List<Reimbursement> getAllReimbursements()
	{
		return reimbursementService.getAllReimbursements();
	}

	@GetMapping("/{id}")
	public Reimbursement getReimbursementById(@PathVariable("id") int id)
	{
		return reimbursementService.getReimbursementById(id);
	}
}