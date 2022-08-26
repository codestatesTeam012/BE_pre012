package com.codestates.pre012.member.dto;

import com.codestates.pre012.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Validated
public class MemberDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {

        @Email(message = "유효한 이메일 형식이어야 합니다.",regexp = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
        @NotBlank(message = "이메일을 입력해주세요")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",message = "비밀번호는 영어, 숫자, 특수문자로 8에서 16자리로 구성되어야 합니다.")
        private String password;
    }


    @Getter
    @AllArgsConstructor
    public static class Patch {

        @Positive
        private long memberId;

        @NotSpace
        private String password;
    }
    //


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        @NotBlank
        @Email(message = "유효한 이메일 형식이어야 합니다.",regexp = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
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