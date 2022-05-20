package com.blog.service;

import java.util.List;

import com.blog.dto.CommentDTO;
import com.blog.exceptions.BlogApiException;
import com.blog.exceptions.ResourceNotFoundException;

public interface CommentService {
	
	public CommentDTO createComment(long postId, CommentDTO commentDTO) throws ResourceNotFoundException;
	
	public List<CommentDTO> getCommentsByPostId(long postId);

	public CommentDTO getCommentById(Long postId, Long commentId) throws ResourceNotFoundException, BlogApiException;
	
	public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentRequest) throws ResourceNotFoundException, BlogApiException;
	
	public void deleteComment(Long postId, Long commentId) throws ResourceNotFoundException, BlogApiException;
	
}
