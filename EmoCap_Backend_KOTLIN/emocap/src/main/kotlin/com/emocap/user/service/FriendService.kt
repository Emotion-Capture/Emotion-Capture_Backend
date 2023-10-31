package com.emocap.user.service

import com.emocap.common.model.CommonResponse
import com.emocap.user.model.dto.FriendDTO
import com.emocap.user.model.dto.FriendIdDTO
import com.emocap.user.model.entity.User
import com.emocap.user.repository.FSimpleUserMapping
import com.emocap.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FriendService (
   private val userRepository: UserRepository
){
    @Transactional
    fun addFriendByNickname(friendDTO: FriendDTO): CommonResponse<List<User>?> {
        val sender = userRepository.findUserByNickname(friendDTO.sender)
        val receiver = userRepository.findUserByNickname(friendDTO.receiver)

        sender.friends.add(receiver)
        receiver.friends.add(sender)

        userRepository.save(sender)
        userRepository.save(receiver)

        return CommonResponse(
            status = 201,
            success = true,
            message = "${sender.nickname}과 ${receiver.nickname}이 친구를 맺었습니다.",
            data = null
        )
    }
    @Transactional
    fun addFriendById(friendIdDTO: FriendIdDTO): CommonResponse<List<User>?> {
        val sender = userRepository.findById(friendIdDTO.sender).get()
        val receiver = userRepository.findById(friendIdDTO.receiver).get()

        sender.friends.add(receiver)
        receiver.friends.add(sender)

        userRepository.save(sender)
        userRepository.save(receiver)

        return CommonResponse(
            status = 201,
            success = true,
            message = "${sender.nickname}과 ${receiver.nickname}이 친구를 맺었습니다.",
            data = null
        )
    }
    fun findFriendById(id: Long): CommonResponse<MutableList<FSimpleUserMapping>> {
        if (!userRepository.existsById(id)) {
            return CommonResponse(
                status = 200,
                success = true,
                message = "${id}은 존재하지 않는 id 입니다.",
                data = null
            )
        }
       val user = userRepository.findFriendsById(id)
       return CommonResponse(
           status = 200,
           success = true,
           message = "${user.getId()}의 친구를 불러옵니다.",
           data = user.getFriends()
       )
   }
    fun findFriendByNickname(nickname: String): CommonResponse<MutableList<FSimpleUserMapping>> {
        if (!userRepository.existsByNickname(nickname)) {
            return CommonResponse(
                status = 200,
                success = true,
                message = "${nickname}은 존재하지 않는 닉네임 입니다.",
                data = null
            )
        }
        val user = userRepository.findFriendsByNickname(nickname)
        return CommonResponse(
            status = 200,
            success = true,
            message = "${nickname}의 친구를 불러옵니다.",
            data = user.getFriends()
        )
    }
    fun deleteFriendByNickname(friendDTO: FriendDTO): CommonResponse<String?> {
        var sender = userRepository.findUserByNickname(friendDTO.sender)
        var receiver = userRepository.findUserByNickname(friendDTO.receiver)

        sender.friends.remove(receiver)
        receiver.friends.remove(sender)

        userRepository.save(sender)
        userRepository.save(receiver)

        return CommonResponse(
            status = 204,
            success = true,
            message = "${friendDTO.sender}와(과) ${friendDTO.receiver}의 친구 관계가 삭제되었습니다.",
            data = null
        )
    }
    fun deleteFriendById(friendIdDTO: FriendIdDTO): CommonResponse<String?> {
        var sender = userRepository.findById(friendIdDTO.sender).get()
        var receiver = userRepository.findById(friendIdDTO.receiver).get()

        sender.friends.remove(receiver)
        receiver.friends.remove(sender)

        userRepository.save(sender)
        userRepository.save(receiver)

        return CommonResponse(
            status = 204,
            success = true,
            message = "${sender.nickname}와(과) ${receiver.nickname}의 친구 관계가 삭제되었습니다.",
            data = null
        )
    }
}