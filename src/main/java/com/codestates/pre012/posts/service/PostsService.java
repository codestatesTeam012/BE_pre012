package com.codestates.pre012.posts.service;

import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.repository.PostsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PostsService {

    private final PostsRepository postsRepository;

    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }


    /**
     * 새로운 글 등록
     */
    public Posts savedPosts(Posts postsPost) {

        return postsRepository.save(postsPost);


    }


    /**
     * 글 수정
     */
    public Posts updatePosts(Posts patchPost) {

        Posts findPosts = existPosts(patchPost.getPostsId());

        Optional.ofNullable(patchPost.getTitle())
                .ifPresent(findPosts::setTitle);
        Optional.ofNullable(patchPost.getContent())
                .ifPresent(findPosts::setContent);

        return postsRepository.save(findPosts);
    }

    /**
     * 특정 글 조회
     */
    public Posts findPost(long postId) {

        return existPosts(postId);
    }

    /**
     * 전체 글 조회
     */
    public Page<Posts> findPosts(int page, int size) {
        return postsRepository.findAll(PageRequest.of
                (page,size, Sort.by("postsId").descending()));
    }


    /**
     * 특정 글 삭제
     */
    public void deletePosts(long postId) {

        Posts findPosts = existPosts(postId);

        postsRepository.delete(findPosts);
    }


    /**
     * PostId 존재하지 않을 경우 Exception
     */
    private Posts existPosts(long postsId) {

        Optional<Posts> existPosts = postsRepository.findById(postsId);

        return existPosts.orElseThrow(() ->
                new RuntimeException("PostId not exist"));
    }
}
