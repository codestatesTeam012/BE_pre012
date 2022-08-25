package com.codestates.pre012.posts.repository;

import com.codestates.pre012.posts.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("select p from Posts p join fetch p.member where p.postId = :postId and p.member.memberId = :memberId")
    public Optional<Posts> find(@Param("postId") Long postId, @Param("memberId") Long memberId);
    //patch로 수정 시 member 식별 + post id 식별을 한 쿼리문 안에 실행하기 위해 @Query 에 fetch join 사용
}
