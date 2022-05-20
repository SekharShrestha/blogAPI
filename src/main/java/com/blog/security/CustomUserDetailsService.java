package com.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entity.Role;
import com.blog.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepo userRepo;

	@Autowired
	public CustomUserDetailsService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
	//find user by name or email, if not found throw UsernameNotFoundException
		com.blog.entity.User user = userRepo.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
								   .orElseThrow(()-> new UsernameNotFoundException("User not found with "+usernameOrEmail));
	//user obj given to Spring's User obj to validate if username and password valid or not
		return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		//convert set of roles into a collection of GrantedAuthority
		return roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
