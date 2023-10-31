package com.emocap.chatroom.repository

import com.emocap.chatroom.model.entity.Chatroom
import com.emocap.user.model.entity.User
import com.emocap.user.repository.FSimpleUserMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

interface ChatroomMapping {
    fun getId(): Long
    fun getCreatedAt(): LocalDateTime
    fun getUsers(): List<FSimpleUserMapping>
}


@Repository
interface ChatroomRepository : JpaRepository<Chatroom, Long> {
    fun findChatroomByUsersContaining(user: User): List<ChatroomMapping?>
    @Query("SELECT c FROM Chatroom  c WHERE c.id = :id")
    fun findById(id: Long?): Chatroom
    fun existsById(id: Long?): Boolean
}