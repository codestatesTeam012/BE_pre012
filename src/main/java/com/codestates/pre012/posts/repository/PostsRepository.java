package com.codestates.pre012.posts.repository;

import com.codestates.pre012.posts.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {


}
