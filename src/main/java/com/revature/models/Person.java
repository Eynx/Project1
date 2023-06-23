package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class Person
{
	// Members

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_first_name")
	private String firstName;
	@Column(name = "user_last_name")
	private String lastName;

	@ManyToOne
	@JoinColumn(name = "user_role_id")
	private Role role;

	@Column(name = "user_username", unique = true)
	private String username;
	@Column(name = "user_password")
	private String password;

	@OneToMany(targetEntity = Reimbursement.class, mappedBy = "person")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Reimbursement> reimbursements;

	// Constructors

	public Person()
	{
	}

	public Person(String firstName, String lastName, Role role, String username, String password)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.username = username;
		this.password = password;
	}

	public Person(int id, String firstName, String lastName, Role role, String username, String password)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.username = username;
		this.password = password;
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

	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Role getRole()
	{
		return role;
	}
	public void setRole(Role role)
	{
		this.role = role;
	}

	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}

	public List<Reimbursement> getReimbursements()
	{
		return reimbursements;
	}
	public void setReimbursements(List<Reimbursement> reimbursements)
	{
		this.reimbursements = reimbursements;
	}

	// Object Overrides

	@Override
	public String toString()
	{
		return "Person{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", role=" + role +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", reimbursements=" + reimbursements +
				'}';
	}
}
