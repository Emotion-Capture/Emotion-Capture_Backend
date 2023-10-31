package com.emocap.common.handler

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun  handleValidationException(ex: MethodArgumentNotValidException) : ResponseEntity<String> {
        return ResponseEntity.badRequest().body("요청 데이터가 올바르지 않습니다.")
    }
}