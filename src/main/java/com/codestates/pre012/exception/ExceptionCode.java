package com.codestates.pre012.exception;

import lombok.Getter;

public enum ExceptionCode {

    INFO_NOT_FOUND(404, "정보가 없습니다."),
    MEMBER_NOT_FOUND(404, "멤버가 존재하지 않습니다."),
    MEMBER_EXIST(404,"멤버가 이미 존재합니다."),
    POSTS_NOT_FOUND(404,"포스트가 존재하지 않습니다. "),
    
    REPLY_NOT_FOUND(404,"댓글이 존재하지 않습니다. "),
    WRONG_MEMBERS_REPLY(401,"본인이 작성하지 않은 댓글은 수정/삭제 불가합니다. "),

    THERE_MUST_BE_AT_LEAST_ONE_TAG(405, "태그는 1개 이상 작성해야 합니다."),

    TAG_NOT_FOUND(404, "존재하지 않는 태그입니다."),

    ONLY_WRITE_UP_TO_THREE(405, "태그는 최대 3개까지 작성할 수 있습니다.");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
