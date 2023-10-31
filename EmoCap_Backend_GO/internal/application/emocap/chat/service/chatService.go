package chat_service

import (
	chat_utils "emocap/internal/application/emocap/chat/utils"
)

func ChatService() {

	for {
		select {
		// 유저가 메시지를 전송함
		case broadcast := <-chat_utils.Broadcast:
			if chat_utils.Hubs[broadcast.ChatroomId] == nil {
				chat_utils.InitHub(broadcast.ChatroomId)
			}
			chat_utils.Hubs[broadcast.ChatroomId].SendBroadCast(&broadcast)

			// 유저가 들어감
		case register := <-chat_utils.Register:
			if chat_utils.Hubs[register.ChatroomId] == nil {
				chat_utils.InitHub(register.ChatroomId)
			}
			chat_utils.Hubs[register.ChatroomId].ClientRegister(register.Nickname)

			// 유저가 나감
		case unregister := <-chat_utils.Unregister:
			chat_utils.Hubs[unregister.ChatroomId].ClientUnregister(unregister.Nickname)

			// 정해놓지 않는 데이터
		case receive := <-chat_utils.Receive:
			chat_utils.ReceiveUnkown(&receive)
		}
	}
}
