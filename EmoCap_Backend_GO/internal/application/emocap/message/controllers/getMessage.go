package message_controllers

import (
	chat_repository "emocap/internal/application/emocap/message/repository"
	"emocap/internal/infrastructure/models"
	"emocap/libs/response"
	"fmt"
	"log"
	"net/http"
	"strconv"

	"github.com/labstack/echo/v4"
)

func GetMessage(c echo.Context) error {
	id := c.Param("roomId")

	roomId, _ := strconv.Atoi(id)

	log.Println("roomid :", roomId)

	message, err := chat_repository.GetMessage(roomId)
	if err != nil {
		log.Println(err)
		myResponse := &response.CommonResponse[error]{
			Status:  http.StatusInternalServerError,
			Success: false,
			Message: fmt.Sprintf("%v의 데이터를 가져오는데 실패함", roomId),
			Data:    err,
		}
		c.JSON(http.StatusBadRequest, myResponse)
		return err
	}

	myResponse := &response.CommonResponse[[]models.Message]{
		Status:  http.StatusOK,
		Success: true,
		Message: "get message",
		Data:    message,
	}

	c.JSON(http.StatusOK, myResponse)

	return nil
}
