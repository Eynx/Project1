package com.revature.controllers;

import com.revature.daos.RoleDAO;
import com.revature.daos.UserDAO;
import com.revature.dtos.AuthResponseDTO;
import com.revature.dtos.LoginDTO;
import com.revature.dtos.RegisterDTO;
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
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO)
	{
		if (userDAO.existsByUsername(registerDTO.getUsername())){
			return new ResponseEntity<String>("User name is Taken:", HttpStatus.BAD_REQUEST);
		}
		Person p = new Person();
		p.setFirstName(registerDTO.getFirstName());
		p.setLastName(registerDTO.getLastName());
		p.setUsername(registerDTO.getUsername());
		p.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

		// Now that we've done all this, let's set the standard role
		Role role = roleDAO.getByName("Employee");

		p.setRole(role);

		// We've built the proper person object so let's save it now
		userDAO.save(p);

		return new ResponseEntity<>("User successfully registered!", HttpStatus.CREATED);
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
