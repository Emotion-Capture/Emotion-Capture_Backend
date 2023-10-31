package com.emocap.common.facade

import com.emocap.chatroom.model.entity.Chatroom
import com.emocap.chatroom.repository.ChatroomMapping
import com.emocap.chatroom.repository.ChatroomRepository
import com.emocap.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class CommonFacade (
    private val chatroomRepository: ChatroomRepository,
    private val userRepository: UserRepository,
){
    fun getChatroomByNickname(nickname: String): List<ChatroomMapping?> {
        val user = userRepository.findUserByNickname(nickname)
        return chatroomRepository.findChatroomByUsersContaining(user)
    }
    fun getChatroomById(id: Long): List<ChatroomMapping?> {
        val user = userRepository.findById(id).get()
        return chatroomRepository.findChatroomByUsersContaining(user)
    }
    fun getChatroomByRoomId(id: Long) : Chatroom {
        return chatroomRepository.findById(id).get()
    }
}