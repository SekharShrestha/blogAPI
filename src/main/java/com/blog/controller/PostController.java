package com.blog.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dto.PostDTO;
import com.blog.dto.PostDtoV2;
import com.blog.entity.PostResponse;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.service.PostService;
import com.blog.utils.Constants;

// INDEX : 

// POST   /v1/posts 	 - Create new post
// GET    /v1/posts		 - Get all posts
// GET    /v1/posts/{id} - Get Posts by Id
// GET    /v2/posts/{id} - Get Posts by Id with tags(V2 feature)
// PUT    /v1/posts/{id} - Update post by id
// DELETE /v1/posts/{id} - Delete post by id

@RestController
@RequestMapping("/api")
public class PostController {

	private PostService postService;

	@Autowired
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	//Create new post
	//@PreAuthorize("hasRole('Admin')") //only admin can access this rest api
	@PostMapping("/v1/posts")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) { //RequestBody converts json request to java obj
																					 //Json request converted to PostDTO obj
		return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED); //com.blog.service.impl.PostServiceImpl 
																						  //line 62
	}

	//Get all posts
	@GetMapping("/v1/posts")
	public PostResponse getAllPost(
			@RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir); // PostServiceImpl line 76
	}

	//Get Posts by Id
	//If not found, then throw ResourceNotFoundException
	@GetMapping("/v1/posts/{id}")
	public ResponseEntity<PostDTO> getPostByIdV1(@PathVariable(name = "id") long id) throws ResourceNotFoundException {
		return ResponseEntity.ok(postService.getPostById(id)); // com.blog.service.impl.PostServiceImpl line 
	}

	//Get Posts by Id with tags(V2 feature)
	@GetMapping("/v2/posts/{id}")
	public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name = "id") long id) throws ResourceNotFoundException {
		PostDTO postDto = postService.getPostById(id);
		PostDtoV2 postDtoV2 = new PostDtoV2();
		postDtoV2.setId(postDto.getId());
		postDtoV2.setTitle(postDto.getTitle());
		postDtoV2.setDescription(postDto.getDescription());
		postDtoV2.setContent(postDto.getContent());
		postDtoV2.setComments(postDto.getComments());
		List<String> tags = new ArrayList<>();
		tags.add("Java");
		tags.add("Spring Boot");
		tags.add("AWS");
		postDtoV2.setTags(tags);
		return ResponseEntity.ok(postDtoV2); // PostServiceImpl line 70
	}

	//Update post by id
	//@PreAuthorize("hasRole('Admin')") //only admin can access this rest api
	@PutMapping("/v1/posts/{id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable(name = "id") long id)
			throws ResourceNotFoundException {
		PostDTO postResponse = postService.updatePost(postDTO, id);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

	//Delete post by id
    //@PreAuthorize("hasRole('Admin')") //only admin can access this rest api
	@DeleteMapping("/v1/posts/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) throws ResourceNotFoundException {
		postService.deleteById(id);
		return new ResponseEntity<>("Deletion completed successfully", HttpStatus.OK);
	}

}
