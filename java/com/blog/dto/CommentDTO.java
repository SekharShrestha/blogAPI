package com.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentDTO {
	
	private long id;
	@NotEmpty(message="Name should not be null")
	private String name;
	
	@NotEmpty(message="Email should not be null")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min=2, message="Comment body must be minimum 2 characters")
	private String body;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	

}
