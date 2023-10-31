package com.emocap.user.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class SigninDTO(
    @NotBlank(message = "닉네임은 필수 입력해야 합니다.")
    val nickname : String,

    @NotBlank(message = "패스워드는 필수 입력해야 합니다.")
    val password : String
)
