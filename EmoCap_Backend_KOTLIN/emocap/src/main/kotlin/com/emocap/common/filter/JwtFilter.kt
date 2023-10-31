package com.emocap.common.filter

import com.emocap.common.service.MyUserDetailService
import com.emocap.common.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val userDetailsService: MyUserDetailService,
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        var nickname: String? = null
        var token: String? = null
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            //token = authorizationHeader.replace("Bearer ", "")
            token = authorizationHeader.substring(7)
            nickname = jwtUtil.getNicknameFromJWT(token)
        }

        if (nickname != null) {
            val userDetail = this.userDetailsService.loadUserByUsername(nickname)
            if (jwtUtil.validateToken(token, userDetail.username)){
                val usernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(userDetail, null, userDetail.authorities)
                usernamePasswordAuthenticationToken.details =
                    WebAuthenticationDetailsSource().buildDetails(request)
                // SecurityContextHolder 에 유저 정보 저장
                SecurityContextHolder.getContext().authentication =
                    usernamePasswordAuthenticationToken
            }
        }

        filterChain.doFilter(request, response)
    }
}