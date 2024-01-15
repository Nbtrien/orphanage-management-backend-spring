package com.graduatebackend.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
    private int status;
    private ErrorDto error;
    private T result;

    public static <T> ResponseDto<T> ok(T result) {
        return ResponseDto.<T>builder()
                .status(HttpStatus.OK.value())
                .result(result)
                .build();
    }

    public static ResponseDto fail(int status, ErrorDto error) {
        return ResponseDto.builder().status(status).error(error).build();
    }

    public static ResponseDto badRequest(ErrorDto error) {
        return ResponseDto.builder().status(HttpStatus.BAD_REQUEST.value()).error(error).build();
    }

    public static ResponseDto serverError(ErrorDto error) {
        return ResponseDto.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(error).build();
    }
}
