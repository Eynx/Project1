package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.models.Person;
import com.revature.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	private final UserDAO userDAO;

	@Autowired
	public CustomUserDetailsService(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Person person = userDAO.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user with that username was found."));

		return new User(person.getUsername(), person.getPassword(), mapRoleToAuthority(person.getRole()));
	}

	private Collection<GrantedAuthority> mapRoleToAuthority(Role role)
	{
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		return grantedAuthorities;
	}
}
