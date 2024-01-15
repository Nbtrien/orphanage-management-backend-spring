package com.graduatebackend.exception;

import com.graduatebackend.dto.ErrorDto;
import com.graduatebackend.dto.ResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
@Order(2)
public class CommonExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(ResponseDto.fail(404, new ErrorDto("404", ex.getMessage())),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(ResponseDto.fail(422, new ErrorDto("422",
                                                                       "Your email address or password is incorrect.")),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleResourceExistedException(ResourceExistedException ex) {
        String code = "409";
        if (ex.getCode() != null) {
            code = ex.getCode();
        }
        return new ResponseEntity<>(ResponseDto.fail(409, new ErrorDto(code, ex.getMessage())),
                                    HttpStatus.CONFLICT);
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleEmailExistedException(EmailExistedException ex) {
        return new ResponseEntity<>(ResponseDto.fail(409, new ErrorDto("409", ex.getMessage())),
                                    HttpStatus.CONFLICT);
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleUserExistsException(UserExistsException ex) {
        return new ResponseEntity<>(ResponseDto.fail(409, new ErrorDto("409", ex.getMessage())),
                                    HttpStatus.CONFLICT);
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String code = "404";
        if (ex.getCode() != null) {
            code = ex.getCode();
        }
        return new ResponseEntity<>(ResponseDto.fail(404, new ErrorDto(code, ex.getMessage())),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleNonVolunteerApplyException(NonVolunteerApplyException ex) {
        return new ResponseEntity<>(ResponseDto.fail(408, new ErrorDto("408", ex.getMessage())),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleApplyEventExceptionException(ApplyEventException ex) {
        String code = "404";
        if (ex.getCode() != null) {
            code = ex.getCode();
        }
        return new ResponseEntity<>(ResponseDto.fail(404, new ErrorDto(code, ex.getMessage())),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ResponseDto.fail(400, new ErrorDto("400", ex.getMessage())),
                                    HttpStatus.BAD_REQUEST);
    }
}
