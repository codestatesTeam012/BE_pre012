package com.codestates.pre012.posts.dto;

import com.codestates.pre012.member.entity.Member;
import lombok.Getter;

@Getter
public class PatchPostsDto {

    private Long postId;

    private Long memberId;

    private String title;

    private String content;



}
