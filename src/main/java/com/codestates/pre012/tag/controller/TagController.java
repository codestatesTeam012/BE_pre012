package com.codestates.pre012.tag.controller;

import com.codestates.pre012.dto.MultiResponseDto;
import com.codestates.pre012.posts.entity.Posts;
import com.codestates.pre012.posts.mapper.PostsMapper;
import com.codestates.pre012.tag.converter.StringToTagDto;
import com.codestates.pre012.tag.entity.Tag;
import com.codestates.pre012.tag.service.TagPostService;
import com.codestates.pre012.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/tag")
public class TagController {

    private final TagPostService tagPostService;
    private final TagService tagService;
    private final PostsMapper postsMapper;
    private final StringToTagDto stringToTagDto;

    @GetMapping
    public ResponseEntity showTags(@RequestParam int page,
                                   @RequestParam int size) {
        Page<Tag> tagPage = tagService.findAll(page, size);
        List<Tag> tags = tagPage.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(tags, tagPage), HttpStatus.OK);
    }

    @GetMapping("/{tag}")
    public ResponseEntity findPosts(@PathVariable("tag") String tag,
                                    @RequestParam int page,
                                    @RequestParam int size) {

        Tag.TagList tagList = stringToTagDto.StringToTagList(tag);
        Page<Posts> postsPage = tagPostService.findPostsByTag(tagList, page, size);
        List<Posts> postsList = postsPage.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(postsMapper.postsToPostsDtoResponses(postsList),postsPage), HttpStatus.OK);
    }
}
