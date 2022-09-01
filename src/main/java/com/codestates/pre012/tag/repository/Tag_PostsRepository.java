package com.codestates.pre012.tag.repository;

import com.codestates.pre012.tag.entity.Tag;
import com.codestates.pre012.tag.entity.Tag_Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface Tag_PostsRepository extends JpaRepository<Tag_Posts, Long> {

    @Query("select t.tag from Tag_Posts t where t.posts.postsId = :postsId")
    List<Tag> findByPostsId(long postsId);

    @Query("select t from Tag_Posts t where t.posts.postsId = :postsId")
    Optional<Tag_Posts> findAllByPostsId(long postsId);
}
