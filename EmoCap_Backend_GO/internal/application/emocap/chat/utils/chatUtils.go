package chat_utils

import (
	"emocap/internal/application/emocap/chat/dto"
	chat_repository "emocap/internal/application/emocap/message/repository"
	"emocap/libs/utils"
	"log"

	"github.com/gorilla/websocket"
)

var (
	Conn       = make(map[string]*websocket.Conn)
	Broadcast  = make(chan dto.Message)
	Register   = make(chan dto.Message)
	Unregister = make(chan dto.Message)
	Receive    = make(chan dto.Message)
)

type Hub struct {
	// // chat_room_id
	// Id int
	// key: user nickname, value: socket conn
	Clients []*websocket.Conn
}

// 허브의 모음집, key: chat_room_id, value: hub
var Hubs = make(map[int]*Hub)

// 모든 client에게 메시지 전송, db에 저장
func (h *Hub) SendBroadCast(m *dto.Message) {

	err := chat_repository.CreateMessage(m)
	if err != nil {
		log.Println(err)
		return
	}

	for _, v := range h.Clients {
		if v != Conn[m.Nickname] {
			m.Event = "Information created in db"
			err := v.WriteJSON(m)
			if err != nil {
				log.Println("[SendBroadCast] write json error : ", err)
				break
			}
		}
	}
}

// 정해놓은 프로토콜에 맞지 않는 데이터를 처리한다.
func ReceiveUnkown(m *dto.Message) {
	log.Println("received : ", m)
}

// chatroom id가 일치하는 hub에 유저를 추가시킨다.
func (h *Hub) ClientRegister(nickname string) {
	h.Clients = append(h.Clients, Conn[nickname])
}

// chatroom id가 일치하는 hub에 유저를 삭제한다.
func (h *Hub) ClientUnregister(nickname string) {
	h.Clients = utils.DeleteSliceValue(h.Clients, Conn[nickname])
}

// hubs에 chatroom id가 존재하지 않을 때 생성시킨다.
func InitHub(chatroomId int) {
	Hubs[chatroomId] = &Hub{}
}

// Hubs에서 chatroom id의  삭제시킨다.
func deleteHub(chatroomId int) {
	Hubs[chatroomId] = nil
}
