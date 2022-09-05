package com.codestates.pre012.tag.repository;

import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.tag.entity.Tag;
import com.codestates.pre012.tag.entity.TagPosts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagPostsRepository extends JpaRepository<TagPosts, Long> {

    @Query("select t from TagPosts t where t.posts.postsId = :postsId")
    Optional<TagPosts> findByPostsPostsId(Long postsId);

    //tagList로 관련된 post 검색
    @Query("select t.posts from TagPosts t where t.tag.tagList = :tagList")
    Page<Posts> findPostsByTag(@Param("tagList")Tag.TagList tagList, Pageable pageable);

}
