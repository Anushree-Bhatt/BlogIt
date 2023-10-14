package controller;


import org.modelmapper.ModelMapper;
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

import entity.Role;
import entity.User;
import exception.ResourceNotFound;
import payload.LoginDTO;
import payload.RegisterUserDTO;
import repository.RoleRepository;
import repository.UserRepository;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ModelMapper modelmapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	//Not Working!!/
	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO){
		//this is how security's /login works!
		Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
		
		//You got to save it in securityContext
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseEntity<String>("User authenticated with naem:"+SecurityContextHolder.getContext().getAuthentication().getName(),HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO registerUserDTO){
		//add check for username exists in DB
		if(userRepository.existsByUsername(registerUserDTO.getUsername())) {
			return new ResponseEntity<String>("Username already exists!!",HttpStatus.CONFLICT);
		}
		
		//add check for email exists in DB
		if(userRepository.existsByEmail(registerUserDTO.getEmail())) {
			return new ResponseEntity<String>("Email already exists!!",HttpStatus.CONFLICT);
		}
		
		//To save this in DB, you got first convert this into User object
		User user = modelmapper.map(registerUserDTO, User.class);
		
		//Before saving set the role!
		Role role = roleRepository.findByName("ROLE_USER").orElseGet(()->{throw new ResourceNotFound("Role", "Name", "Role_USER");});
		user.addRole(role);
		
		//Encode the pwd before saving
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User res = userRepository.save(user);
		return new ResponseEntity<String>(res.toString(),HttpStatus.ACCEPTED);
		
		
	}
	
}
