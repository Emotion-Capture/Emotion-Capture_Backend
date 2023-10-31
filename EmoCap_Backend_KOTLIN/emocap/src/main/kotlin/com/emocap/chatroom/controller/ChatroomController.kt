package com.emocap.chatroom.controller

import com.emocap.chatroom.model.dto.ChatroomDTO
import com.emocap.chatroom.model.entity.Chatroom
import com.emocap.chatroom.repository.ChatroomMapping
import com.emocap.chatroom.service.ChatroomService
import com.emocap.common.model.CommonResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "chatroom API", description = "채팅방 기능 API 입니다.")
@RestController
@RequestMapping("/api/chatroom")
class ChatroomController(
    private val chatroomService: ChatroomService,
) {
    @GetMapping("/nickname/{nickname}")
    @Operation(summary = "get chatroom by user nickname", description = "유저의 닉네임으로 유저가 속한 채팅방 정보를 가져옵니다.")
    fun getUsers(@PathVariable nickname: String): CommonResponse<List<ChatroomMapping?>> {
        return chatroomService.getChatroomByNickname(nickname)
    }
    @GetMapping("/id/{id}")
    @Operation(summary = "get chatroom by user id", description = "유저의 아이디로 유저가 속한 채팅방 정보를 가져옵니다.")
    fun getUsers(@PathVariable id: Long): CommonResponse<List<ChatroomMapping?>> {
        return chatroomService.getChatroomById(id)
    }
    @PostMapping("/create")
    @Operation(summary = "[TEST] create chatting room", description = "방을 생성합니다.")
    fun createChatroom(): CommonResponse<Chatroom?> {
        return chatroomService.createChatroom()
    }
    @PostMapping("")
    @Operation(summary = "create chatting room and add user", description = "chatroom_id를 포함하면 그 방에 유저를 추가하고 없다면 방을 새로 생성하고 유저를 추가합니다.")
    fun createChatroomAddUser(@RequestBody chatroomDTO: ChatroomDTO): CommonResponse<Chatroom?> {
        return chatroomService.addUser(chatroomDTO)
    }
    @PutMapping("")
    @Operation(summary = "add user in chatting room", description = "채팅방에 id나 nickname 으로 유저를 추가합니다.")
    fun addUser(@RequestBody chatroomDTO: ChatroomDTO): CommonResponse<Chatroom?> {
        return chatroomService.addUser(chatroomDTO)
    }
    @PutMapping("/exit")
    @Operation(summary = "exit user in chatting room", description = "채팅방의 id나 nickname 으로 유저를 추방합니다.")
    fun exitChatroom(@RequestBody chatroomDTO: ChatroomDTO): CommonResponse<Chatroom?> {
        return chatroomService.exitChatroom(chatroomDTO)
    }
    @DeleteMapping("{id}")
    @Operation(summary = "delete chatting room", description = "채팅방을 삭제합니다.")
    fun deleteChatroom(@PathVariable id: Long): CommonResponse<String?> {
        return chatroomService.deleteChatroom(id)
    }
}