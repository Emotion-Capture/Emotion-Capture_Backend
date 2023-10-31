package controllers

import (
	chat_controllers "emocap/internal/application/emocap/chat/controllers"
	chat_service "emocap/internal/application/emocap/chat/service"
	message_controllers "emocap/internal/application/emocap/message/controllers"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	echoSwagger "github.com/swaggo/echo-swagger"
	_ "github.com/swaggo/echo-swagger/example/docs"
)

// echo server routing
func EchoRoute(e *echo.Echo) {
	// 미들웨어
	e.Use(middleware.Logger())
	e.Use(middleware.Recover())
	e.Use(middleware.CORS())

	// messages API
	messageGroup := e.Group("/api/messages")
	// path param, roomId로 방의 메시지 가져오기
	messageGroup.GET("/:roomId", message_controllers.GetMessage)

	// chatting API
	chatGroup := e.Group("/api/chat")
	//e.GET("/chat", controllers.ChatController, my_midlerware.JWTMiddleware)
	chatGroup.GET("", chat_controllers.ChatController)
	go chat_service.ChatService()

	// swagger API
	e.GET("/swagger/*", echoSwagger.WrapHandler)
}
