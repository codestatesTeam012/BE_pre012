package com.codestates.pre012.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }


    @Getter
    @AllArgsConstructor
    public static class Patch {

        private long memberId;

        @Email
        @NotBlank
        private String password;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        @Email
        @NotBlank
        private String email;

        @Email
        @NotBlank
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {

        @NotBlank
        private long memberId;

        @Email
        @NotBlank
        private String email;

    }
}