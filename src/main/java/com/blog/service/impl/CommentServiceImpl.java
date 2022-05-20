package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blog.dto.CommentDTO;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exceptions.BlogApiException;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.repo.CommentRepo;
import com.blog.repo.PostRepo;
import com.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepo commentRepo;
	private PostRepo postRepo;
	private ModelMapper mapper;

	@Autowired
	public CommentServiceImpl(CommentRepo commentRepo, PostRepo postRepo, ModelMapper mapper) {
		this.commentRepo = commentRepo;
		this.postRepo = postRepo;
		this.mapper = mapper;
	}

	
	// convert entity to DTO
	private CommentDTO mapToDTO(Comment comment) {
		CommentDTO commentDto = mapper.map(comment, CommentDTO.class);
		
	  /*CommentDTO commentDto = new CommentDTO();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());*/

		return commentDto;
	}

	// convert DTO to entity
	private Comment mapToEntity(CommentDTO commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
		
	  /*Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());*/
		
		return comment;
	}
	
	
	//Create new comment
	@Override
	public CommentDTO createComment(long postId, CommentDTO commentDTO) throws ResourceNotFoundException {
	//data in dto transferred to entity
		Comment comment = mapToEntity(commentDTO);
	//retrieve Post entity by id, if not found then throw ResourceNotFoundException
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
	//set post to Comment entity, ie the post in which the comment's to be added
		comment.setPost(post);
	//save Comment entity to db, 
		Comment newComment = commentRepo.save(comment);
	//data in entity transferred to dto, which is sent back to Rest, to show the saved comment details
		return mapToDTO(newComment);
	}

	
	//Get comments by post id
	@Override
	public List<CommentDTO> getCommentsByPostId(long postId) {
	//retrieve comments by post id
		List<Comment> comments = commentRepo.findByPostId(postId);
	//convert list of comment entities to list of comment dtos
		return comments.stream().map(comment->mapToDTO(comment)).collect(Collectors.toList());
	}

	
	//Get comment by comment id
	@Override
	public CommentDTO getCommentById(Long postId, Long commentId) throws ResourceNotFoundException, BlogApiException {
	//retrieve Post entity by id
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
	//retrieve comment by id
		Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}
		
		return mapToDTO(comment);
				
	}

	
	//Update comment by comment id
	@Override
	public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentRequest) throws ResourceNotFoundException, BlogApiException {
	//retrieve Post entity by id
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
	//retrieve comment by id
		Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
				
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}
	
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		Comment updatedComment = commentRepo.save(comment);
		return mapToDTO(updatedComment);
	}


	@Override
	public void deleteComment(Long postId, Long commentId) throws ResourceNotFoundException, BlogApiException {
	//retrieve Post entity by id
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
	//retrieve comment by id
		Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}
		
		commentRepo.delete(comment);
		
	}

}
