package com.codestates.pre012.posts.controller;


import com.codestates.pre012.config.oauth.PrincipalDetails;
import com.codestates.pre012.dto.MultiResponseDto;
import com.codestates.pre012.dto.SingleResponseDto;
import com.codestates.pre012.posts.dto.PostsDto;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.mapper.PostsMapper;
import com.codestates.pre012.posts.service.PostsService;
import com.codestates.pre012.reply.Reply;
import com.codestates.pre012.reply.ReplyMapper;
import com.codestates.pre012.reply.ReplyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@Validated // queryParameter 유효성 검증에 필요
public class PostsController {

    private final PostsService postsService;
    private final ReplyService replyService; //post상세페이지에 같이 댓글 보여야 하므로 추가
    private final PostsMapper mapper;

    private final ReplyMapper replyMapper;

    public PostsController(PostsService postsService, ReplyService replyService, PostsMapper mapper, ReplyMapper replyMapper) {
        this.postsService = postsService;
        this.replyService = replyService;
        this.mapper = mapper;
        this.replyMapper = replyMapper;
    }

    @PostMapping("/create")
    public ResponseEntity createPosts(@Valid @RequestBody PostsDto.Post posts, @AuthenticationPrincipal PrincipalDetails principal) {

        Posts findPosts = mapper.postsPostDtoToPosts(posts);
        Posts response = postsService.savedPosts(findPosts, principal.getMember());

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.CREATED);
    }


    @PatchMapping("/patch")
    public ResponseEntity patchPosts(@Valid @RequestBody PostsDto.Patch posts, @AuthenticationPrincipal PrincipalDetails principal) {


        posts.setPostsId(posts.getPostsId());
        Posts response = postsService.updatePosts(mapper.postsPatchDtoToPosts(posts), principal.getMember());

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsDtoResponse(response)), HttpStatus.OK);
    }

    @GetMapping("/{posts-id}")
    public ResponseEntity viewPosts(@PathVariable("posts-id") @Positive Long postId,
                                    @RequestParam int replyPage,
                                    @RequestParam int replySize) {

        Posts response = postsService.lookPosts(postId);

        Page<Reply> replies = replyService.getReplies(replyPage, replySize, postId);
        List<Reply> replyList = replies.getContent();
        List<Object> list = List.of(response,replyList);

        return new ResponseEntity<>(new MultiResponseDto<>(list, replies), HttpStatus.OK);
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
    public ResponseEntity deletePosts(@PathVariable("posts-Id") @Positive Long postId, @AuthenticationPrincipal PrincipalDetails principal) {

        postsService.deletePosts(postId, principal.getMember());

        return new ResponseEntity<>("삭제 완료", HttpStatus.NO_CONTENT);
    }
}
