package com.blog.dto;

import java.util.Objects;

//DTO obj are the actual data sent to client from server
//DTOs help hide the implementation details of jpa entities
//Exposing jpa entities through rest apis can be a security issue

public class SignUpDTO {
	
	private String name;
	private String userName;
	private String email;
	private String password;
	
	public SignUpDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SignUpDTO(String name, String userName, String email, String password) {
		super();
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, name, password, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SignUpDTO other = (SignUpDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(userName, other.userName);
	}

	@Override
	public String toString() {
		return "SignUpDTO [name=" + name + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ "]";
	}
	
	

}
