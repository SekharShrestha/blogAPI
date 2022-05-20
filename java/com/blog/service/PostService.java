package com.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.dto.PostDTO;
import com.blog.entity.PostResponse;
import com.blog.exceptions.ResourceNotFoundException;

public interface PostService {
	
	public PostDTO createPost(PostDTO postDTO);
	public PostResponse getAllPosts(int pageNo, int paseSize, String sortBy, String sortDir);
	public PostDTO getPostById(long id) throws ResourceNotFoundException;
	public PostDTO updatePost(PostDTO postDTO, long id) throws ResourceNotFoundException;
	public void deleteById(long id) throws ResourceNotFoundException;
}
