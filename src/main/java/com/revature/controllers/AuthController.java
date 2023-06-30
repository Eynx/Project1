package com.revature.controllers;

import com.revature.daos.RoleDAO;
import com.revature.daos.UserDAO;
import com.revature.dtos.AuthResponseDTO;
import com.revature.dtos.LoginDTO;
import com.revature.dtos.RegisterDTO;
import com.revature.exceptions.RoleNotFoundException;
import com.revature.models.Person;
import com.revature.models.Role;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController
{
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final UserDAO userDAO;
	private final RoleDAO roleDAO;
	private final PasswordEncoder passwordEncoder;
	private final JwtGenerator jwtGenerator;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserService userService, UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator)
	{
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.userDAO = userDAO;
		this.roleDAO = roleDAO;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterDTO registerDTO)
	{
		if (userDAO.existsByUsername(registerDTO.getUsername())) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		Person person = new Person();
		person.setFirstName(registerDTO.getFirstName());
		person.setLastName(registerDTO.getLastName());
		person.setUsername(registerDTO.getUsername());
		person.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

		Role role = roleDAO.findByName("Customer").orElseThrow(() -> new RoleNotFoundException("There was an error assigning the default role."));
		person.setRole(role);

		userDAO.save(person);

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerDTO.getUsername(), registerDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwtToken = jwtGenerator.generateToken(authentication);
		return new ResponseEntity<AuthResponseDTO>(new AuthResponseDTO(jwtToken), HttpStatus.OK);
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
