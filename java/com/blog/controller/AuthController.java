package com.blog.controller;

import java.util.Collection;
import java.util.Collections;

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

import com.blog.dto.LoginDTO;
import com.blog.dto.SignUpDTO;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.repo.RoleRepo;
import com.blog.repo.UserRepo;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/auth/v1/signin")
	public ResponseEntity<String> authenticatoUser(@RequestBody LoginDTO loginDto){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
																		   loginDto.getUserNameOrEmail(), loginDto.getPassword()));																	   
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("User signed in successfully", HttpStatus.OK);
	}
	
	@PostMapping("/auth/v1/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto){
		
		//if userName exists in db
		if(userRepo.existsByUserName(signUpDto.getUserName())) {
			return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
		}
		
		//if email exists in db
		if(userRepo.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("Email id already has an account", HttpStatus.BAD_REQUEST);
		}
		
		//create user object
		User user = new User();
		user.setName(signUpDto.getName());
		user.setEmail(signUpDto.getEmail());
		user.setUserName(signUpDto.getUserName());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		
		Role roles = roleRepo.findByName("Admin").get();
		user.setRoles(Collections.singleton(roles));
		
		userRepo.save(user);
		
		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
	}
	
	

}
