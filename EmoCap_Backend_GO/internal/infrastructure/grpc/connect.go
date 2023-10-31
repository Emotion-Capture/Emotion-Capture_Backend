package grpc

import (
	"errors"
	"log"

	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

var (
	grpcConnection *grpc.ClientConn
)

func GrpcConn() *grpc.ClientConn {
	return grpcConnection
}

func Connect(address string) error {

	conn, err := grpc.Dial(address, grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		return errors.New("[gRPC] kotlin 서버와의 연결 실패 err: " + err.Error())
	}

	log.Println("[grpc connected success]")

	grpcConnection = conn

	return nil
}

// grpc 연결 해제
func Disconnect() {
	if grpcConnection != nil {
		grpcConnection.Close()
	}
}
