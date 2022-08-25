package com.codestates.pre012.posts.dto;

import com.codestates.pre012.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostPostsDto {

    private Long memberId;

    private String title;

    private String content;

}
