package com.codestates.pre012.posts.controller;


import com.codestates.pre012.config.oauth.PrincipalDetails;
import com.codestates.pre012.dto.MultiResponseDto;
import com.codestates.pre012.dto.SingleResponseDto;
import com.codestates.pre012.member.entity.Member;
import com.codestates.pre012.posts.dto.PostsDto;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.mapper.PostsMapper;
import com.codestates.pre012.posts.service.PostsService;
import com.codestates.pre012.tag.converter.StringToTagDto;
import com.codestates.pre012.tag.dto.TagDto;
import com.codestates.pre012.tag.entity.Tag;
import com.codestates.pre012.tag.entity.TagPosts;
import com.codestates.pre012.tag.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/posts")
@Validated // queryParameter 유효성 검증에 필요
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final PostsMapper mapper;
    private final TagMapper tagMapper;
    private final StringToTagDto stringToTagDto;

    @PostMapping("/create")
    public ResponseEntity createPosts(@Valid @RequestBody PostsDto.Post posts,
                                      @AuthenticationPrincipal PrincipalDetails principal) {

        Member member = principal.getMember();
        Posts findPosts = mapper.postsPostDtoToPosts(posts);
        List<TagDto.Post> responseList = stringToTagDto.tagListToTagDtoResponse(posts.getTags());

        List<Tag> tags = tagMapper.tagDtoPostToTags(responseList);
        Posts response = postsService.savedPosts(findPosts,member, tags);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsResponse(response, tags)), HttpStatus.CREATED);
    }

    @PatchMapping("/{posts-id}")
    public ResponseEntity patchPosts(@PathVariable("posts-id") @Positive Long postsId,
                                     @Valid @RequestBody PostsDto.Post posts,
                                     @AuthenticationPrincipal PrincipalDetails principal) {


        Posts requestPosts = mapper.postsPostDtoToPosts(posts);
        List<TagDto.Post> responseList = stringToTagDto.tagListToTagDtoResponse(posts.getTags());

        List<Tag> tags = tagMapper.tagDtoPostToTags(responseList);
        Posts response = postsService.updatePosts(postsId ,requestPosts ,principal.getMember(),tags);
        List<Tag> tagList = response.getTagPosts().stream()
                .map(TagPosts::getTag).collect(Collectors.toList());


        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToPostsResponse(response, tagList)), HttpStatus.OK);

    }

    @GetMapping("/{posts-id}")
    public ResponseEntity viewPosts(@PathVariable("posts-id") @Positive Long postsId) {

        Posts response = postsService.lookPosts(postsId);
        List<Tag> tags = response.getTagPosts().stream()
                .map(TagPosts::getTag).collect(Collectors.toList());
        String username = response.getMember().getUsername();

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.postsToSearchResponse(response, tags, username)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findPosts(@RequestParam @Positive int page,
                                    @RequestParam @Positive int size) {

        Page<Posts> pagePosts = postsService.findAllPosts(page - 1, size);

        List<Posts> posts = pagePosts.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postsToPostsDtoResponses(posts), pagePosts), HttpStatus.OK);
    }

    @DeleteMapping("/{posts-Id}")
    public ResponseEntity deletePosts(@PathVariable("posts-Id") @Positive Long postId,
                                      @AuthenticationPrincipal PrincipalDetails principal) {

        System.out.println("delete controller");
        postsService.deletePosts(postId, principal.getMember());


        return new ResponseEntity<>("삭제 완료", HttpStatus.NO_CONTENT);
    }


}
