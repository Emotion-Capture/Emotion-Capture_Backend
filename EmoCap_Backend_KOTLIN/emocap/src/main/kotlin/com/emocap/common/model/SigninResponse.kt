package com.emocap.common.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SigninResponse<T> (
    @JsonProperty("status")
    val status : Int?,

    @JsonProperty("success")
    val success: Boolean,

    @JsonProperty("message")
    val message: String?,

    @JsonProperty("token")
    val token: T?
)