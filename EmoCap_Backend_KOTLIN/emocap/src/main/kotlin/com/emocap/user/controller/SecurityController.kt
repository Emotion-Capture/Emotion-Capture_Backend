package com.emocap.user.controller

import com.emocap.common.model.CommonResponse
import com.emocap.common.model.SigninResponse
import com.emocap.user.model.dto.SigninDTO
import com.emocap.user.model.dto.UserDTO
import com.emocap.user.model.entity.User
import com.emocap.user.service.SecurityService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "user security API", description = "user 의 회원가입, 로그인 API 입니다.")
@RestController
@RequestMapping("/api/users")
class SecurityController (
    private val securityService: SecurityService
) {
    @PostMapping("/signup")
    @Operation(summary = "signup", description = "회원가입")
    fun signup(@Validated @RequestBody userDTO: UserDTO) : CommonResponse<User> {
        return securityService.signup(userDTO)
    }
    @PostMapping("/signin")
    @Operation(summary = "login", description = "로그인")
    fun signin(@Validated @RequestBody signinDTO: SigninDTO) : SigninResponse<String> {
        return securityService.signin(signinDTO)
    }
}