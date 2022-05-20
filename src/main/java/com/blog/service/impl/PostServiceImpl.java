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
	//data in dto transferred to entity
		Post post = mapToEntity(postDTO); 
	//post entity saved into db
		Post newPost = postRepo.save(post);
	//data in entity transferred to dto, which is sent back to Rest, to show the saved post details
		PostDTO postResponse = mapToDTO(newPost);		
		return postResponse;
	}


	
	//Get all posts
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir) {
	//if sortDir.equalsIgnoreCase(asc), then ascending order, else descending order
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending() :
					Sort.by(sortBy).descending();
	//create Pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
	//interface postRepo->JpaRepository->PagingAndSortingRepository - Iterable<T> findAll(Sort sort), Page<T> findAll(Pageable, pageable)
	//returns a page of entities meeting the paging restriction in the pageable obj
		Page<Post> posts = postRepo.findAll(pageable);
	//get content of Page obj(ie. posts), gets all the data from db in that page no
		List<Post> listOfPosts = posts.getContent();
	//for all the collected contents in listOfPosts, map to dto, and put them in List content
		List<PostDTO> content = listOfPosts.stream().map(post->mapToDTO(post)).collect(Collectors.toList());
	//returns the pageNo, pageSize, totalElements, totalPages and isLast values to the rest, with the content above them
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
	//(Optional<>)find by id, if not found then throw ResourceNotFoundException
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
	//convert post to dto which is then returned to rest
		return mapToDTO(post);
	}

	
	//Update post by id
	@Override
	public PostDTO updatePost(PostDTO postDTO, long id) throws ResourceNotFoundException {
	//(Optional<>)find by id, if not found then throw ResourceNotFoundException
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
	//set updated values from postDTO to post(ie. replace orig with updated values)
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
	//save post(ie. updated content) to db, which is sent back to Rest, to show the updated post details
		Post updatePost = postRepo.save(post);
		return mapToDTO(updatePost);
	}

	
	// Delete post by id
	@Override
	public void deleteById(long id) throws ResourceNotFoundException {
	//(Optional<>)find by id, if not found then throw ResourceNotFoundException
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
	//Delete post
		postRepo.delete(post);
		
	}	

}
