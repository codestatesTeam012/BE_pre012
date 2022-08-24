package com.codestates.pre012.posts.dto;

import com.codestates.pre012.member.entity.Member;
import lombok.*;
import lombok.Builder;

import javax.persistence.Column;

public class PostsDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        private String title;

        private String content;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {

        private long postsId;

        private String title;

        private String content;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private long postsId;

        private String title;

        private String content;

    }
}