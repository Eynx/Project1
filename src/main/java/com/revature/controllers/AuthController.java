package com.revature.controllers;

import com.revature.daos.RoleDAO;
import com.revature.dtos.AuthResponseDTO;
import com.revature.dtos.LoginDTO;
import com.revature.models.Person;
import com.revature.security.JwtGenerator;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final RoleDAO roleDAO;
	private final PasswordEncoder passwordEncoder;
	private final JwtGenerator jwtGenerator;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserService userService, RoleDAO roleDAO, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator)
	{
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.roleDAO = roleDAO;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Person person)
	{
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		person.setRole(roleDAO.findByName("Customer").orElseThrow());
		userService.addPerson(person);
		return new ResponseEntity<String>("Successfully registered the new account.", HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO)
	{
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwtToken = jwtGenerator.generateToken(authentication);
		return new ResponseEntity<AuthResponseDTO>(new AuthResponseDTO(jwtToken), HttpStatus.OK);
	}
}
