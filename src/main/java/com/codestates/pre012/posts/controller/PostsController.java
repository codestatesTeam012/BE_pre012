package com.codestates.pre012.posts.controller;

import com.codestates.pre012.dto.MultiResponseDto;
import com.codestates.pre012.dto.SingleResponseDto;
import com.codestates.pre012.member.service.MemberService;
import com.codestates.pre012.posts.OrderBy;
import com.codestates.pre012.posts.dto.PatchPostsDto;
import com.codestates.pre012.posts.dto.PostPostsDto;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.mapper.PostsMapper;
import com.codestates.pre012.posts.service.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/posts")
@RestController
public class PostsController {

    private final PostsService postsService;


    private final PostsMapper mapper;

    public PostsController(PostsService postsService, PostsMapper mapper) {
        this.postsService = postsService;
        this.mapper = mapper;
    }
    @PostMapping("/posts")
    public ResponseEntity createPosts(@RequestBody PostPostsDto postPostsDto) {
        Posts posts = mapper.postPostsDtoToPosts(postPostsDto);
        Posts createPosts = postsService.savedPosts(posts, postPostsDto.getMemberId());
        //member 식별을 위해 postservice에 memberId 멤버변수로 추가

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToResponsePostsDto(createPosts),"post created"), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{posts-id}")
    public ResponseEntity updatePosts(@PathVariable("posts-id")Long postsId,
                                      @RequestBody PatchPostsDto patchPostsDto) {
        Posts posts = mapper.patchPostsDtoToPosts(patchPostsDto);

        Posts updatePosts = postsService.updatePosts(posts, postsId, patchPostsDto.getMemberId());
        //posts, member 식별을 위해 postservice에 postsId, memberId 멤버변수로 추가

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToResponsePostsDto(updatePosts),"post updated"),HttpStatus.OK);
    }

    @GetMapping("/posts_view/{posts-id}")
    public ResponseEntity viewPosts(@PathVariable("posts-id") Long postsId) {
        Posts posts = postsService.findByPostId(postsId);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToResponsePostsDto(posts)),HttpStatus.OK);
    }

    @GetMapping("/question_view")
    public ResponseEntity questionList(@RequestParam int page,
                                       @RequestParam int size,
                                       @RequestParam OrderBy orderBy) { //정렬방법(OLD/NEWEST/ALPHABETICAL)
        System.out.println(orderBy.name());
        Page<Posts> postsPage = postsService.findAllPosts(page, size, orderBy);
        List<Posts> postsList = postsPage.toList();

        return new ResponseEntity<>(new MultiResponseDto<>(mapper.postsToResponsePostsDto(postsList), postsPage),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{posts-id}")
    public ResponseEntity deletePosts(@PathVariable("posts-id")Long postsId) {
        postsService.deletePosts(postsId);

        return new ResponseEntity<>("posts deleted",HttpStatus.NO_CONTENT);
    }
}
