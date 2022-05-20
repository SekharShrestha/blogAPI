package com.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dto.CommentDTO;
import com.blog.exceptions.BlogApiException;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	private CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	//Create new comment
	@PostMapping("/v1/posts/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId") long postId,
													@Valid @RequestBody CommentDTO commentDto) throws ResourceNotFoundException {
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);

	}
	
	
	@GetMapping("/v1/posts/{postId}/comments")
	public List<CommentDTO> getCommentsByPostId(@PathVariable(value="postId") Long postId){
		return commentService.getCommentsByPostId(postId);
		
	}
	
	
	@GetMapping("/v1/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value="postId") Long postId, 
													 @PathVariable(value="id") Long commentId) throws ResourceNotFoundException, BlogApiException{
		
		CommentDTO commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
		
	}
	
	@PutMapping("/v1/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable(value="postId") Long postId, 
			                                        @PathVariable(value="id")Long commentId, 
			                                        @Valid @RequestBody CommentDTO commentDto) throws ResourceNotFoundException, BlogApiException{
		
		CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDto);
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/v1/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable(value="postId") Long postId, 
												@PathVariable(value="id") Long commentId) throws ResourceNotFoundException, BlogApiException{
		
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
		
	}
}
