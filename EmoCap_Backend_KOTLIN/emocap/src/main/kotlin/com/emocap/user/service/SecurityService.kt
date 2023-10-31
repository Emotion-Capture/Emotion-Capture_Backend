package com.emocap.user.service

import com.emocap.common.model.CommonResponse
import com.emocap.common.model.SigninResponse
import com.emocap.common.util.JwtUtil
import com.emocap.user.model.dto.SigninDTO
import com.emocap.user.model.dto.UserDTO
import com.emocap.user.model.entity.User
import com.emocap.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SecurityService (
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil
    ) {
    @Transactional
    fun signup(userDTO: UserDTO) : CommonResponse<User> {
        if (userRepository.existsByNickname(userDTO.nickname)){
            return CommonResponse(
                status = 400,
                success = false,
                message = "이미 존재하는 닉네임입니다.",
                data = null
            )
        }

        userDTO.password =  passwordEncoder.encode(userDTO.password)

        val user = User(
            nickname = userDTO.nickname,
            password = userDTO.password,
            role = userDTO.judgeRole()
        )

        userRepository.save(user)

        return CommonResponse(
            status = 201,
            success = true,
            message = "회원가입에 성공했습니다.",
            data = user
        )
    }
    fun signin(signinDTO: SigninDTO) : SigninResponse<String> {
        val simpleUser = userRepository.findSimpleUserByNickname(signinDTO.nickname)

        if (!passwordEncoder.matches(signinDTO.password, simpleUser.getPassword())) {
            return SigninResponse(
                status = 401,
                success = false,
                message = "비밀번호가 틀립니다.",
                token = null
            )
        }

        val token = jwtUtil.generateToken(signinDTO.nickname)

        return SigninResponse(
            status = 200,
            success = true,
            message = "로그인에 성공했습니다.",
            token = token
        )
    }
}