package com.codestates.pre012.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {

        private String email;

        private String password;

    }


    @Getter
    @AllArgsConstructor
    public static class Patch {

        private long memberId;

        private String password;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        private String email;

        private String password;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {

        private long memberId;

        private String email;

    }


}