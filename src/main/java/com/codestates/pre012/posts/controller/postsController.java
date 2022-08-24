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

import java.util.List;

@RestController
@RequestMapping("/v1/posts")
public class postsController {

    private final PostsService postsService;
    private final PostsMapper mapper;

    public postsController(PostsService postsService, PostsMapper mapper) {
        this.postsService = postsService;
        this.mapper = mapper;
    }

    /**
     * 회원 관리 ( 글 작성 / 글 수정 /특정 글 조회 / 전체 글 목록 / 글 삭제 )
     */
    @PostMapping
    public ResponseEntity join(@RequestBody PostsDto.Post posts) {

        Posts findPosts = mapper.postsPostDtoToPosts(posts);
        Posts response = postsService.savedPosts(findPosts);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.CREATED);
    }


    @PatchMapping("/{posts-Id}")
    public ResponseEntity login(@PathVariable("posts-Id") Long postId,
                                @RequestBody PostsDto.Patch posts) {

        posts.setPostsId(postId);
        Posts response = postsService.updatePosts(mapper.postsPatchDtoToPosts(posts));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.OK);
    }

    @GetMapping("/{posts-Id}")
    public ResponseEntity findPost(@PathVariable("posts-Id") Long postId) {

        Posts response = postsService.findPost(postId);


        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findPosts(@RequestParam int page,
                                    @RequestParam int size) {

        Page<Posts> pagePosts = postsService.findPosts(page - 1, size);

        List<Posts> posts = pagePosts.getContent();

        for (Posts post : posts) {
            System.out.println(post.getContent());
        }

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postsToPostsDtoResponses(posts), pagePosts),
                HttpStatus.OK);
    }


    @DeleteMapping("/{posts-Id}")
    public ResponseEntity deletePost(@PathVariable("posts-Id") Long postsId) {

        postsService.deletePosts(postsId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
