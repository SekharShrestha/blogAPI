package com.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.Post;

public interface PostRepo extends JpaRepository<Post, Long>{

}
