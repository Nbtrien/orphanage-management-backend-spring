package com.graduatebackend.exception;

import com.graduatebackend.dto.ErrorDto;
import com.graduatebackend.dto.ResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
@Order(1)
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        String code = "400";
        int httpStatus = 400;
        if (ex.getBindingResult().hasErrors()) {
            switch (ex.getBindingResult().getAllErrors().get(0).getCode()) {
                case "NotBlank":
                    httpStatus = 403;
                    code = "403";
                    break;
                case "ValidPassword":
                    httpStatus = 422;
                    code = "422";
                    break;
                case "NotEmpty":
                    httpStatus = 404;
                    code = "404";
                    break;
                case "Email":
                    httpStatus = 400;
                    code = "400";
                    break;
                case "PasswordMatch":
                    httpStatus = 433;
                    code = "433";
                    break;
            }
        }
        String errorMsg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(ResponseDto.badRequest(new ErrorDto(code, errorMsg)), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ResponseDto.badRequest(new ErrorDto("400", "Request body is required.")),
                                    HttpStatus.BAD_REQUEST);
    }
}
