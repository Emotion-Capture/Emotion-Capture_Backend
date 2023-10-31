package models

import (
	"github.com/kamva/mgm/v3"
)

type Message struct {
	mgm.DefaultModel
	ChatroomId int    `bson:"room_id"`
	UserId     int    `bson:"user_id"`
	Nickname   string `bson:"nickname"`
	Content    string `bson:"content"`
}
