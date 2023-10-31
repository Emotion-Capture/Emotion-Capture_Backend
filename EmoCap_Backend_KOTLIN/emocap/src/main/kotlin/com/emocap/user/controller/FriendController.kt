package com.emocap.user.controller

import com.emocap.common.model.CommonResponse
import com.emocap.user.model.dto.FriendDTO
import com.emocap.user.model.dto.FriendIdDTO
import com.emocap.user.model.entity.User
import com.emocap.user.repository.FSimpleUserMapping
import com.emocap.user.service.FriendService
import com.emocap.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "friend API", description = "유저의 친구 기능 API 입니다.")
@RestController
@RequestMapping("/api/users/friends")
class FriendController (
    private val userService: UserService,
    private val friendService: FriendService,
) {
    @PostMapping("/nickname")
    @Operation(summary = "add friendship by nickname", description = "유저의 닉네임으로 친구관계를 생성합니다")
    fun addFriendByNickname(@RequestBody friendDTO: FriendDTO): CommonResponse<List<User>?> {
        return friendService.addFriendByNickname(friendDTO)
    }
    @PostMapping("/id")
    @Operation(summary = "add friendship by id", description = "유저의 id로 친구관계를 생성합니다")
    fun addFriendByNickname(@RequestBody friendIdDTO: FriendIdDTO): CommonResponse<List<User>?> {
        return friendService.addFriendById(friendIdDTO)
    }
    @GetMapping("/id/{id}")
    @Operation(summary = "get friendship by id", description = "유저의 id로 유저의 친구관계를 가져옵니다.")
    fun findFriendById(@PathVariable id:Long): CommonResponse<MutableList<FSimpleUserMapping>> {
        return friendService.findFriendById(id)
    }
    @GetMapping("/nickname/{nickname}")
    @Operation(summary = "get friendship by nickname", description = "유저의 nickname 으로 유저의 친구관계를 가져옵니다.")
    fun findFriendByNickname(@PathVariable nickname:String): CommonResponse<MutableList<FSimpleUserMapping>> {
        return friendService.findFriendByNickname(nickname)
    }
    @DeleteMapping("/nickname")
    @Operation(summary = "delete friendship by nickname", description = "유저의 nickname 으로 친구관계를 삭제합니다.")
    fun deleteFriendByNickname(@RequestBody friendDTO: FriendDTO): CommonResponse<String?> {
        return friendService.deleteFriendByNickname(friendDTO)
    }
    @DeleteMapping("/id")
    @Operation(summary = "delete friendship by id", description = "유저의 id 으로 친구관계를 삭제합니다.")
    fun deleteFriendById(@RequestBody friendIdDTO: FriendIdDTO): CommonResponse<String?> {
        return friendService.deleteFriendById(friendIdDTO)
    }
}