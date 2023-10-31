package com.emocap.common.service

import com.emocap.common.model.Role
import com.emocap.common.model.rolesToAuthorities
import com.emocap.user.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MyUserDetailService (
    private val userRepository: UserRepository
        ):UserDetailsService {
    override fun loadUserByUsername(nickname: String?): UserDetails {
        // 사용자 정보를 데이터베이스에서 검색하거나 다른 저장소에서 가져오는 로직
        val user = userRepository.findSimpleUserByNickname(nickname)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다. : $nickname")

        // hasRole 함수에서 상위 역할은 하위 역할도 수행할 수 있도록한다.
        val roles: Collection<Role> = when (val userRole = user.getRole()) {
            Role.ADMIN -> listOf(Role.ADMIN, Role.USER, Role.GUEST)
            Role.USER -> listOf(Role.USER, Role.GUEST)
            else -> listOf(userRole)
        }

        val authorities: Collection<GrantedAuthority?> = rolesToAuthorities(roles)

        return User(
            user.getNickname(),
            user.getPassword(),
            authorities
        )
    }
}