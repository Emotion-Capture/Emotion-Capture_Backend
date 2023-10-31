package com.emocap.user.service

import com.emocap.common.model.CommonResponse
import com.emocap.user.model.entity.User
import com.emocap.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
) {
    fun getUsers() : CommonResponse<List<User>> {
        return CommonResponse(
            status = 200,
            success = true,
            message = "모든 유저를 반환합니다",
            data = userRepository.findAll()
        )
    }
    fun getUserById(id: Long) : CommonResponse<User> {
        val user = userRepository.findById(id).get()
        return CommonResponse(
            status = 200,
            success = true,
            message = "$id 유저를 반환합니다",
            data = user
        )
    }
    fun getUserByNickName(nickname: String) : CommonResponse<User> {
        return CommonResponse(
            status = 200,
            success = true,
            message = "$nickname 유저를 반환합니다",
            data = userRepository.findUserByNickname(nickname)
        )
    }
    fun deleteByNickName(nickname: String) : CommonResponse<String> {
        userRepository.deleteByNickname(nickname)
        return CommonResponse(
            status = 204,
            success = true,
            message = "${nickname}이 삭제되었습니다.",
            data = null
        )
    }
    fun deleteById(id: Long) : CommonResponse<String> {
        userRepository.deleteById(id)
        return CommonResponse(
            status = 204,
            success = true,
            message = "${id}가 삭제되었습니다.",
            data = null
        )
    }
}