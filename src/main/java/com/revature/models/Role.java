package com.revature.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role
{
	// Members

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "role_name", unique = true)
	private String name;

	// Constructors

	public Role()
	{
	}

	public Role(String name)
	{
		this.name = name;
	}

	public Role(int id, String name)
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
		return "Role{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
