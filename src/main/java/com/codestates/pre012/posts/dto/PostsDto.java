package com.codestates.pre012.posts.dto;

import com.codestates.pre012.member.dto.MemberDto;
import com.codestates.pre012.reply.dto.ReplyDto;
import com.codestates.pre012.tag.dto.TagDto;
import lombok.*;
import javax.validation.constraints.NotBlank;

import java.util.List;

public class PostsDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        @NotBlank(message = "공백이 될 수 없습니다.")
        private String title;

        @NotBlank(message = "공백이 될 수 없습니다.")
        private String content;

        private List<String> tags;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    public static class PostsResponse {

        private long postsId;
        private String title;
        private String content;

        private List<TagDto.Response> tags;

        private int view;

        private List<ReplyDto.Response> replies;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SearchResponse {

        private long postsId;
        private String title;
        private String content;
        private int view;
        private String username;
        private List<TagDto.Response> tags;
        private List<ReplyDto.Response> replies;

    }
}