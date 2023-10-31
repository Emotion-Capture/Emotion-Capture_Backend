package com.emocap.user.repository

import com.emocap.common.model.Role
import com.emocap.user.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

interface SimpleUserMapping {
    fun getId(): Long
    fun getNickname(): String
    fun getPassword(): String
    fun getRole(): Role
}
interface FriendsMapping {
    fun getId(): Long
    fun getNickname(): String
    fun getFriends(): MutableList<FSimpleUserMapping>
}
interface FSimpleUserMapping {
    fun getId(): Long
    fun getNickname(): String
    fun getRole(): Role
}

@Repository
interface UserRepository : JpaRepository<User, Long> {
    //@Query("SELECT id, password, nick_name, role FROM USER WHERE nick_name = :nickName")
    @Query("SELECT u.id as id, u.nickname as nickname, u.password as password, u.role as role FROM User u WHERE u.nickname = :nickname")
    fun findSimpleUserByNickname(nickname: String?): SimpleUserMapping
    fun existsById(id: Long?): Boolean
    fun existsByNickname(nickname: String?): Boolean
    fun findUserByNickname(nickname: String?): User
    fun findById(id: Long?): User
    fun findByNickname(nickname: String?): User
    fun deleteByNickname(nickname: String?)
    //------------------------------------------------------------
    fun findFriendsById(id: Long?): FriendsMapping
    fun findFriendsByNickname(nickname: String?): FriendsMapping
}