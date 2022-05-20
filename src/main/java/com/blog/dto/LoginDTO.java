package com.blog.dto;

import java.util.Objects;

//DTO obj are the actual data sent to client from server
//DTOs help hide the implementation details of jpa entities
//Exposing jpa entities through rest apis can be a security issue

public class LoginDTO {
	
	private String userNameOrEmail;
	private String password;
	
	public LoginDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginDTO(String userNameOrEmail, String password) {
		super();
		this.userNameOrEmail = userNameOrEmail;
		this.password = password;
	}

	public String getUserNameOrEmail() {
		return userNameOrEmail;
	}

	public void setUserNameOrEmail(String userNameOrEmail) {
		this.userNameOrEmail = userNameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, userNameOrEmail);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginDTO other = (LoginDTO) obj;
		return Objects.equals(password, other.password) && Objects.equals(userNameOrEmail, other.userNameOrEmail);
	}

	@Override
	public String toString() {
		return "LoginDTO [userNameOrEmail=" + userNameOrEmail + ", password=" + password + "]";
	}
	
	
	

}
