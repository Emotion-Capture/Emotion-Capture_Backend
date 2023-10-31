package com.emocap.common.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class Role {
    ADMIN,
    USER,
    GUEST
}

fun rolesToAuthorities(roles: Collection<Role>): Collection<GrantedAuthority?> {
    return roles.map { role ->
        SimpleGrantedAuthority("ROLE_$role")
    }
}