package com.revature.dtos;

public class ReimbursementDTO
{
	private int amount;
	private String description;

	public ReimbursementDTO()
	{
	}

	public ReimbursementDTO(int amount, String description)
	{
		this.amount = amount;
		this.description = description;
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

	@Override
	public String toString()
	{
		return "ReimbursementDTO{" +
				"amount=" + amount +
				", description='" + description + '\'' +
				'}';
	}
}
