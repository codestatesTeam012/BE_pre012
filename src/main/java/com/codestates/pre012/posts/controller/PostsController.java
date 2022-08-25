package com.codestates.pre012.posts.controller;


import com.codestates.pre012.dto.MultiResponseDto;
import com.codestates.pre012.dto.SingleResponseDto;
import com.codestates.pre012.posts.dto.PostsDto;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.mapper.PostsMapper;
import com.codestates.pre012.posts.service.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/posts")
public class PostsController {

    private final PostsService postsService;
    private final PostsMapper mapper;

    public PostsController(PostsService postsService, PostsMapper mapper) {
        this.postsService = postsService;
        this.mapper = mapper;
    }

    @PostMapping("/board")
    public ResponseEntity createPosts(@Validated @RequestBody PostsDto.Post posts) {

        Posts response = postsService.savedPosts(mapper.postsPostDtoToPosts(posts));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.CREATED);
    }


    @PatchMapping("/patch")
    public ResponseEntity patchPosts(@Validated @RequestBody PostsDto.Patch posts) {

        Posts response = postsService.updatePosts(mapper.postsPatchDtoToPosts(posts));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.OK);
    }

    @GetMapping("/{posts-id}")
    public ResponseEntity viewPosts(@PathVariable("posts-id") Long postId) {

        Posts response = postsService.lookPost(postId);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findPosts(@RequestParam int page,
                                    @RequestParam int size) {

        Page<Posts> pagePosts = postsService.findAllPosts(page - 1, size);

        List<Posts> response = pagePosts.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postsToPostsDtoResponses(response), pagePosts), HttpStatus.OK);
    }


    @DeleteMapping("/{posts-Id}")
    public ResponseEntity deletePosts(@PathVariable("posts-Id") Long postId) {

        postsService.deletePosts(postId);

        return new ResponseEntity<>("삭제 완료",HttpStatus.NO_CONTENT);
    }
}
