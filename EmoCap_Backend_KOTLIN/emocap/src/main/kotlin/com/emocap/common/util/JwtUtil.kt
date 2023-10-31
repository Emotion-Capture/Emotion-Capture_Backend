package com.emocap.common.util

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class JwtUtil (
    @Value("security.jwt.secret")
    private var secretKey : String
    ){
    // 토큰 생성
    fun generateToken(nickname: String): String {
        return Jwts.builder()
            .setSubject(nickname)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(Date().time + 60 * 60)) // 1시간
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    // JWT에서 유저 닉네임 추출
    fun getNicknameFromJWT(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    //  JWT 검증
    fun validateToken(token: String?, nickname: String): Boolean {
        var tokenNickname = ""
        try {
            tokenNickname = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
        } catch (e: UnsupportedJwtException) {      // 지원하지 않는 토큰
            return false
        } catch (e: MalformedJwtException) {        // 손상된 토큰
            return false
        } catch (e: SignatureException) {           // 시그니처 검증에 실패한 토큰
            return false
        } catch (e: ExpiredJwtException) {          // 만료된 토큰
            return false
        }
        return nickname == tokenNickname && !isTokenExpired(token)
    }

    fun isTokenExpired(token: String?): Boolean {
        val tokenDate = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.expiration
        return tokenDate.before(Date(System.currentTimeMillis()))
    }
}