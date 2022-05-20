package com.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("192053"));
		System.out.println(passwordEncoder.encode("sqop"));
		System.out.println(passwordEncoder.encode("admin"));


	}

}
