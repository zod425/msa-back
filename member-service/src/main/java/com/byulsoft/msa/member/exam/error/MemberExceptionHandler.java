package com.byulsoft.msa.member.exam.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class MemberExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> exceptionHandler(final MethodArgumentNotValidException e) {
        log.info("ExceptionHandler 실행");
        Map<String, Object> error = new HashMap<>();
        error.put("message", "유효성 검사 실패 : "
                + e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
