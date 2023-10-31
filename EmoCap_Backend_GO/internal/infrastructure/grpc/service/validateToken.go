package service

import (
	"context"
	common "emocap/build/common"
	"log"
	"time"

	"emocap/internal/infrastructure/grpc"
)

// jwt 토큰을 체크하고 boolean을 리턴한다.
func ValidateToken(token string) bool {

	commonClient := common.NewCommonProtoServiceClient(grpc.GrpcConn())

	ctx, cancel := context.WithTimeout(context.Background(), time.Second)
	defer cancel()

	jwtReq := &common.JWTRequest{Token: token}

	r, err := commonClient.ValidateToken(ctx, jwtReq)
	if err != nil {
		log.Println(err)
		return false
	}
	if !r.Success {
		return false
	}
	return true
}
