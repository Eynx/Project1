package com.revature.controllers;

import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reimbursements")
@CrossOrigin(origins = "http://localhost:3000")
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

	@PostMapping
	public boolean addReimbursement(@RequestBody Reimbursement reimbursement)
	{
		return reimbursementService.addReimbursement(reimbursement);
	}

	@GetMapping("/{id}")
	public Reimbursement getReimbursementById(@PathVariable("id") int id)
	{
		return reimbursementService.getReimbursementById(id);
	}

	@PostMapping("/{id}/approve")
	public ResponseEntity<String> approveReimbursementById(@PathVariable("id") int id)
	{
		if(reimbursementService.approveReimbursementByID(id)) {
			return new ResponseEntity<>("Successfully approved the reimbursement.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Couldn't find the reimbursement to approve.", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/{id}/deny")
	public ResponseEntity<String> denyReimbursementById(@PathVariable("id") int id)
	{
		if(reimbursementService.denyReimbursementByID(id)) {
			return new ResponseEntity<>("Successfully approved the reimbursement.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Couldn't find the reimbursement to approve.", HttpStatus.NOT_FOUND);
		}
	}
}
