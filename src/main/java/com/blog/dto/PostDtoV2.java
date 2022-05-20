package com.blog.dto;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//DTO obj are the actual data sent to client from server
//DTOs help hide the implementation details of jpa entities
//Exposing jpa entities through rest apis can be a security issue

public class PostDtoV2 {
	
private Long id;
	
	@NotEmpty
	@Size(min=2, message="Post description should have atleast 2 characters")
	private String description;
	
	@NotEmpty
	private String content;
	
	@NotEmpty
	@Size(min=2, message="Post title should have atleast 2 characters")
	private String title;
	
	private Set<CommentDTO> comments;
	
	private List<String> tags;

	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	

}
