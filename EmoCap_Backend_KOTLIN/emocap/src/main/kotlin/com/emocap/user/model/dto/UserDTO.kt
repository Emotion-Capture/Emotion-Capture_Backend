package com.emocap.user.model.dto

import com.emocap.common.model.Role
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class UserDTO(
    @NotBlank(message = "닉네임은 필수 입력해야 합니다.")
    var nickname: String,

    @NotBlank(message = "패스워드는 필수 입력해야 합니다.")
    var password: String,

    var role: String?
) {
    fun judgeRole() : Role {
        return when (this.role) {
            "ADMIN" -> Role.ADMIN
            "USER" -> Role.USER
            "GUEST" -> Role.GUEST
            else -> Role.USER
        }
    }
}
