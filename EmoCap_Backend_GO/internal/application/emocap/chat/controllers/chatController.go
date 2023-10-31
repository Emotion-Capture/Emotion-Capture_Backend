package chat_controllers

import (
	"emocap/internal/application/emocap/chat/dto"
	chat_utils "emocap/internal/application/emocap/chat/utils"
	"log"

	"github.com/gorilla/websocket"
	"github.com/labstack/echo/v4"
)

var (
	upgrader = websocket.Upgrader{
		ReadBufferSize:  1024,
		WriteBufferSize: 1024,
	}
)

func ChatController(c echo.Context) error {

	ws, err := upgrader.Upgrade(c.Response(), c.Request(), nil)
	if err != nil {
		return err
	}

	defer ws.Close()

	for {
		msg := new(dto.Message)

		err := ws.ReadJSON(msg)
		if err != nil {
			log.Println("[chatController] error: ", err)
			break
		} else {
			chat_utils.Conn[msg.Nickname] = ws
		}

		switch msg.Event {
		case "broadcast":
			chat_utils.Broadcast <- *msg
		case "register":
			chat_utils.Register <- *msg
		case "unregister":
			chat_utils.Unregister <- *msg
		default:
			chat_utils.Receive <- *msg
		}
	}
	return nil
}
