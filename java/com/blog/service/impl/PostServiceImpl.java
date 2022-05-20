package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.dto.PostDTO;
import com.blog.entity.Post;
import com.blog.entity.PostResponse;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.repo.PostRepo;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	private PostRepo postRepo;
	private ModelMapper mapper;
	
	@Autowired
	public PostServiceImpl(PostRepo postRepo, ModelMapper mapper) {
		super();
		this.postRepo = postRepo;
		this.mapper = mapper;
	}
	
	//convert entity to DTO 
	private PostDTO mapToDTO(Post post) {
		PostDTO postDto = mapper.map(post, PostDTO.class);
		
	  /*PostDTO postDto = new PostDTO();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());*/
		
		return postDto;
	}
	
	//convert DTO to entity
	private Post mapToEntity(PostDTO postDto) {
		Post post = mapper.map(postDto, Post.class);
		
	  /*Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());*/
		
		return post;
	}

	
	//Create new post
	@Override
	public PostDTO createPost(PostDTO postDTO) {
		Post post = mapToEntity(postDTO);
		
		//post entity saved into db
		Post newPost = postRepo.save(post);
		
		PostDTO postResponse = mapToDTO(newPost);		
		
		return postResponse;
	}


	
	//Get all posts
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending() :
					Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepo.findAll(pageable);
		
		List<Post> listOfPosts = posts.getContent();
		List<PostDTO> content = listOfPosts.stream().map(post->mapToDTO(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}

	
	//Get posts by Id
	@Override
	public PostDTO getPostById(long id) throws ResourceNotFoundException {
		
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		return mapToDTO(post);
	}

	
	//Update post by id
	@Override
	public PostDTO updatePost(PostDTO postDTO, long id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		
		Post updatePost = postRepo.save(post);
		return mapToDTO(updatePost);
	}

	
	// Delete post by id
	@Override
	public void deleteById(long id) throws ResourceNotFoundException {
		
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		postRepo.delete(post);
		
	}	

}
