package com.emocap.user.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FriendDTO(
    var sender: String,
    var receiver: String,
)

data class FriendIdDTO(
    var sender: Long,
    var receiver: Long,
)