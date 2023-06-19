package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "statuses")
public class Status
{
	// Members

	@Id
	@Column(name = "status_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "status_name", unique = true)
	private String name;

	// Constructors

	public Status()
	{
	}

	public Status(String name)
	{
		this.name = name;
	}

	public Status(int id, String name)
	{
		this.id = id;
		this.name = name;
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	// Object Overrides

	@Override
	public String toString()
	{
		return "Status{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
