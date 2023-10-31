package com.emocap.grpc.service

import com.emocap.common.service.MyUserDetailService
import com.emocap.common.util.JwtUtil
import com.emocap.commonproto.CommonProtoServiceGrpcKt
import com.emocap.commonproto.JWTRequest
import com.emocap.commonproto.JWTResponse
import net.devh.boot.grpc.server.service.GrpcService

//@GrpcService(interceptors = [LogGrpcInterceptor::class])
@GrpcService
class GrpcCommonService(
    private val jwtUtil: JwtUtil,
    private val userDetailService: MyUserDetailService,
) : CommonProtoServiceGrpcKt.CommonProtoServiceCoroutineImplBase() {
    override suspend fun validateToken(jwtRequest: JWTRequest): JWTResponse {
        var token: String? = null
        var nickname: String? = null
        if (jwtRequest.token.startsWith("Bearer")) {
            token = jwtRequest.token.substring(7)
            nickname = jwtUtil.getNicknameFromJWT(token)
        }

        if(nickname != null) {
            val userDetail = this.userDetailService.loadUserByUsername(nickname)
            if (jwtUtil.validateToken(token, userDetail.username)) {
               return JWTResponse.newBuilder()
                   .setSuccess(true)
                   .build()
            }
        }
        return JWTResponse.newBuilder()
            .setSuccess(false)
            .build()
    }
}

