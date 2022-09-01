package com.codestates.pre012.posts.repository;


import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.reply.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Modifying
    @Query("update Posts p set p.view = p.view + 1 where p.postsId = :id")
    int updateView(Long id);

//    @Query()
//    Optional<Posts> findPosts(long postsId);

}
