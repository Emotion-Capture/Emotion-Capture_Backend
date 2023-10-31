package com.emocap.user.controller

import com.emocap.common.model.CommonResponse
import com.emocap.user.model.entity.User
import com.emocap.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "user API", description = "user 기능 API 입니다.")
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("")
    @Operation(summary = "get all user", description = "모든 유저 정보를 가져옵니다.")
    fun getUsers() : CommonResponse<List<User>> {
        return userService.getUsers()
    }
    @GetMapping("/id/{id}")
    @Operation(summary = "get user by id", description = "id로 유저 정보를 가져옵니다.")
    fun getUserById(@PathVariable id: Long) : CommonResponse<User> {
        return userService.getUserById(id)
    }
    @GetMapping("/nickname/{nickname}")
    @Operation(summary = "get user by nickname", description = "nickname 으로 유저 정보를 가져옵니다.")
    fun getUserByNickName(@PathVariable nickname: String) : CommonResponse<User> {
        return userService.getUserByNickName(nickname)
    }
    @DeleteMapping("/nickname/{nickname}")
    @Operation(summary = "delete user by nickname", description = "nickname 으로 유저를 삭제합니다.")
    fun deleteByNickName(@PathVariable nickname: String) : CommonResponse<String> {
        return userService.deleteByNickName(nickname)
    }
    @DeleteMapping("id/{id}")
    @Operation(summary = "delete user by id", description = "id 으로 유저를 삭제합니다.")
    fun deleteById(@PathVariable id: Long) : CommonResponse<String> {
        return userService.deleteById(id)
    }
}