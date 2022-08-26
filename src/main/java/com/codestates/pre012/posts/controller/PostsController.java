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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
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

    /**
     * 글 관리 ( 글 작성 / 글 수정 /특정 글 조회 / 전체 글 목록 / 글 삭제 )
     */
    @PostMapping("/board")
    public ResponseEntity createPosts(@RequestBody @Valid PostsDto.Post posts) {

        Posts findPosts = mapper.postsPostDtoToPosts(posts);
        Posts response = postsService.savedPosts(findPosts);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.CREATED);
    }


    @PatchMapping("/patch")
    public ResponseEntity patchPosts(@RequestBody @Valid PostsDto.Patch posts) {

        posts.setPostsId(posts.getPostsId());
        Posts response = postsService.updatePosts(mapper.postsPatchDtoToPosts(posts));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.OK);
    }

    @GetMapping("/{posts-id}")
    public ResponseEntity viewPosts(@PathVariable("posts-id") @Positive Long postId) {

        Posts response = postsService.lookPosts(postId);


        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findPosts(@RequestParam @Positive int page,
                                    @RequestParam @Positive int size) {

        Page<Posts> pagePosts = postsService.findAllPosts(page - 1, size);

        List<Posts> posts = pagePosts.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postsToPostsDtoResponses(posts), pagePosts),
                HttpStatus.OK);
    }


    @DeleteMapping("/{posts-Id}")
    public ResponseEntity deletePosts(@PathVariable("posts-Id") @Positive Long postId) {

        postsService.deletePosts(postId);

        return new ResponseEntity<>("삭제 완료",HttpStatus.NO_CONTENT);
    }
}
