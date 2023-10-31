package chat_repository

import (
	"context"
	"emocap/internal/application/emocap/chat/dto"
	"emocap/internal/infrastructure/db/mongo"
	"emocap/internal/infrastructure/models"
	"errors"
	"log"
	"time"

	"go.mongodb.org/mongo-driver/bson"
)

func CreateMessage(m *dto.Message) error {
	err := mongo.Messages().Create(&models.Message{
		ChatroomId: m.ChatroomId,
		UserId:     m.UserId,
		Nickname:   m.Nickname,
		Content:    m.Content,
	})
	if err != nil {
		return errors.New("[CreateMessage] error: " + err.Error())
	}
	return nil
}

func GetMessage(id int) ([]models.Message, error) {
	messages := []models.Message{}
	filter := bson.M{"room_id": id}
	ctx, _ := context.WithTimeout(context.Background(), time.Duration(time.Second*3))

	err := mongo.Messages().SimpleFindWithCtx(ctx, &messages, filter)
	if err != nil {
		log.Println(err)
		return nil, errors.New("[GetMessage] error: " + err.Error())
	}
	return messages, nil
}
