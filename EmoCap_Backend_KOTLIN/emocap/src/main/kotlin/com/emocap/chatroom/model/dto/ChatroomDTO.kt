package com.emocap.chatroom.model.dto

data class ChatroomDTO (
    var chatroom_id: Long?,
    var user_id: MutableList<Long?>? = mutableListOf(),
    var user_nickname: MutableList<String?>? = mutableListOf()
) {}

