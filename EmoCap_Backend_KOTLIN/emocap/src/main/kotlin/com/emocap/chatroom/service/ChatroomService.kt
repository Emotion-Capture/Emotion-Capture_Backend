package com.emocap.chatroom.service

import com.emocap.common.facade.CommonFacade
import com.emocap.chatroom.model.dto.ChatroomDTO
import com.emocap.chatroom.model.entity.Chatroom
import com.emocap.chatroom.repository.ChatroomMapping
import com.emocap.chatroom.repository.ChatroomRepository
import com.emocap.common.model.CommonResponse
import com.emocap.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatroomService (
    private val chatroomRepository: ChatroomRepository,
    private val userRepository: UserRepository,
    private val chatroomFacade: CommonFacade,
){
    fun getChatroomByNickname(nickname: String): CommonResponse<List<ChatroomMapping?>> {
        return CommonResponse (
            status = 200,
            success = true,
            message = "${nickname}의 채팅방을 반환합니다.",
            data = chatroomFacade.getChatroomByNickname(nickname)
        )
    }
    fun getChatroomById(id: Long): CommonResponse<List<ChatroomMapping?>> {
        return CommonResponse (
            status = 200,
            success = true,
            message = "${id}의 채팅방을 반환합니다.",
            data = chatroomFacade.getChatroomById(id)
        )
    }
    fun createChatroom(): CommonResponse<Chatroom?> {
        return CommonResponse(
            status = 201,
            success = true,
            message = "",
            data = chatroomRepository.save(Chatroom())
        )
    }
    @Transactional
    fun addUser(chatroomDTO: ChatroomDTO): CommonResponse<Chatroom?> {
        var chatroom: Chatroom? = null
        chatroom = if (chatroomDTO.chatroom_id != null) {
            if (!chatroomRepository.existsById(chatroomDTO.chatroom_id)){
                return CommonResponse(
                    status = 400,
                    success = false,
                    message = "방이 존재하지 않습니다.",
                    data = null
                )
            } else{
                chatroomRepository.findById(chatroomDTO.chatroom_id)
            }
        } else {
            chatroomRepository.save(Chatroom())
        }
        // id로 보냈을 때
        if(chatroomDTO.user_id?.get(0) != null || chatroomDTO.user_nickname?.get(0) == null){
            chatroomDTO.user_id?.map { it ->
                val user = userRepository.findById(it)
                // 유저가 존재하고 users에 존재하지 않을 때 추가
                if(userRepository.existsById(it) && !chatroom.users.contains(user)) chatroom.users.add(userRepository.findById(it))
            } // nickname 으로 보냈을 때
        } else if(chatroomDTO.user_id?.get(0) == null || chatroomDTO.user_nickname!![0] != null) {
            chatroomDTO.user_nickname!!.map { it ->
                val user = userRepository.findByNickname(it)
                // 유저가 존재하고 users에 존재하지 않을 때 추가
                if(userRepository.existsByNickname(it) && !chatroom.users.contains(user)) chatroom.users.add(userRepository.findByNickname(it))
            } // 두개 다 아닐 때
        } else {
            return CommonResponse(
                status = 400,
                success = false,
                message = "id와 nickname 모두 오지 않았습니다.",
                data = null
            )
        }
        return CommonResponse(
            status = 201,
            success = true,
            message = "정상적으로 처리 되었습니다.",
            data = chatroomRepository.save(chatroom)
        )
    }
    fun deleteChatroom(id: Long): CommonResponse<String?> {
        chatroomRepository.deleteById(id)
        return CommonResponse(
            status = 204,
            success = true,
            message = "${id}가 삭제되었습니다.",
            data = null
        )
    }
    @Transactional
    fun exitChatroom(chatroomDTO: ChatroomDTO): CommonResponse<Chatroom?> {
        var chatroom = chatroomRepository.findById(chatroomDTO.chatroom_id)

        // id 으로 보냈을 때
        if(chatroomDTO.user_id?.get(0) != null || chatroomDTO.user_nickname?.get(0) == null){
            chatroomDTO.user_id?.map { it ->
                chatroom.users.remove(userRepository.findById(it))
            }
             // nickname 으로 보냈을 때
        } else if(chatroomDTO.user_id?.get(0) == null || chatroomDTO.user_nickname!![0] != null) {
            chatroomDTO.user_nickname!!.map { it ->
                chatroom.users.remove(userRepository.findByNickname(it))
            } // 두개 다 아닐 때
        } else {
            return CommonResponse(
                status = 400,
                success = false,
                message = "id와 nickname 모두 오지 않았습니다.",
                data = null
            )
        }
        return CommonResponse(
            status = 200,
            success = true,
            message = "유저를 ${chatroomDTO.chatroom_id}에서 추방시켰습니다.",
            data = chatroom
        )
    }
}