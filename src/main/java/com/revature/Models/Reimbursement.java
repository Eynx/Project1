package com.revature.models;

import javax.persistence.*;

@Entity
@Table(name = "reimbursements")
public class Reimbursement
{
	// Members

	@Id
	@Column(name = "reimbursement_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "reimbursement_amount")
	private int amount;

	@Column(name = "reimbursement_description")
	private String description;

	@ManyToOne(targetEntity = Person.class)
	@JoinColumn(name = "reimbursement_user_id")
	private Person user;

	@ManyToOne(targetEntity = Status.class)
	@JoinColumn(name = "reimbursement_status_id")
	private Status status;

	// Constructors

	public Reimbursement()
	{
	}

	public Reimbursement(int id, int amount, String description)
	{
		this.id = id;
		this.amount = amount;
		this.description = description;
	}

	// Getters and Setters

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}

	public int getAmount()
	{
		return amount;
	}
	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}

	public Person getUser()
	{
		return user;
	}
	public void setUser(Person user)
	{
		this.user = user;
	}

	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}

	// Object Overrides

	@Override
	public String toString()
	{
		return "Reimbursement{" +
				"id=" + id +
				", amount=" + amount +
				", description='" + description + '\'' +
				", user=" + user +
				", status=" + status +
				'}';
	}
}
