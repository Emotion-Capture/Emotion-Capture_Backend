package main

import (
	controller "emocap/internal/application/emocap"
	mongodb "emocap/internal/infrastructure/db/mongo"
	"emocap/internal/infrastructure/grpc"
	"emocap/libs/security"
	"log"

	echo "github.com/labstack/echo/v4"
	_ "github.com/swaggo/echo-swagger/example/docs"
)

// @title Swagger Example API
// @version 1.0
// @description This is a sample server Petstore server.
// @termsOfService http://swagger.io/terms/

// @contact.name API Support
// @contact.url http://www.swagger.io/support
// @contact.email support@swagger.io

// @license.name Apache 2.0
// @license.url http://www.apache.org/licenses/LICENSE-2.0.html

// @host petstore.swagger.io
// @BasePath /v2
func main() {
	// initialize env structure
	err := security.InitEnv()
	if err != nil {
		log.Fatal("[env] init error: ", err)
	}

	// mongo 서버 연결 시도
	err = mongodb.Connent()
	if err != nil {
		log.Fatal("[db] mongo connect error: ", err)
	}

	// gRPC 연결 시도
	err = grpc.Connect(security.Env().KotlinAddress)
	if err != nil {
		log.Fatal("[grpc] connect error: ", err)
	}
	defer func() {
		grpc.GrpcConn().Close()
	}()

	// 에코 생성
	e := echo.New()

	// 라우팅
	controller.EchoRoute(e)

	// starting server at localhost:5050
	e.Logger.Fatal(e.Start(":5050"))
}
