package dto

import (
	"github.com/kamva/mgm/v3"
)

type Message struct {
	mgm.DefaultModel `json:"-"`
	ChatroomId       int    `json:"chatroom_id"`
	UserId           int    `json:"user_id"`
	Nickname         string `json:"nickname"`
	Content          string `json:"content"`
	Event            string `json:"event"`
}
