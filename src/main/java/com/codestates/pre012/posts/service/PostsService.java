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

    public Posts savedPosts(Posts postsPost) {

        return postsRepository.save(postsPost);
    }

    public Posts updatePosts(Posts patchPost) {

        Posts findPosts = existPosts(patchPost.getPostsId());

        Optional.ofNullable(patchPost.getTitle())
                .ifPresent(findPosts::setTitle);
        Optional.ofNullable(patchPost.getContent())
                .ifPresent(findPosts::setContent);

        return postsRepository.save(findPosts);
    }


    public Posts lookPost(long postId) {

        return existPosts(postId);
    }


    public Page<Posts> findAllPosts(int page, int size) {

        return postsRepository.findAll(PageRequest.of(page,size, Sort.by("postsId").descending()));
    }


    public void deletePosts(long postId) {

        Posts findPosts = existPosts(postId);

        postsRepository.delete(findPosts);
    }


    private Posts existPosts(long postsId) {

        Optional<Posts> existPosts = postsRepository.findById(postsId);

        return existPosts.orElseThrow(() ->
                new RuntimeException("PostId not exist"));
    }
}
