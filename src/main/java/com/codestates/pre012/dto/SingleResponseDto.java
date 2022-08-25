package com.codestates.pre012.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponseDto<T> {
    private T data;

    private String message;

    public SingleResponseDto(T data) {
        this.data = data;
    }
}
