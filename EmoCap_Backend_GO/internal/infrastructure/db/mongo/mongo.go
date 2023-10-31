package mongo

import (
	"emocap/internal/infrastructure/models"
	"emocap/libs/security"
	"errors"
	"log"

	"github.com/kamva/mgm/v3"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var (
	messages *mgm.Collection
)

// Messages 컬렉션을 리턴한다.
func Messages() *mgm.Collection {
	return messages
}

// mongo 서버와 연결하고 컬렉션을 불러온다.
func Connent() error {

	// Setup the mgm default config
	err := mgm.SetDefaultConfig(nil, security.Env().MongoDBNAME, options.Client().ApplyURI(security.Env().MongoURI))
	if err != nil {
		return errors.New("[mongo] mongo db 서버와의 연결 실패 err: " + err.Error())
	}

	log.Println("[mongo connected success]")

	messages = mgm.Coll(&models.Message{})

	return nil
}

/*// mongodb 커넥션 구조체
type MongoConn struct {
	Messages *mgm.Collection
}
// 팩토리 메서드를 사용해 생성
func NewMongoConn() *MongoConn {
	return &MongoConn{}
}
// 싱글톤 패턴
func (m *MongoConn) Message() *mgm.Collection {
	return m.Messages
}
// mongo 서버와 연결하고 initConn(mongoconn 인스턴스에 연결된 컬렉션 할당) 함수 실행
func (m *MongoConn) ConnentMongo() error {
	// Setup the mgm default config
	err := mgm.SetDefaultConfig(nil, security.Env().MongoDBNAME, options.Client().ApplyURI(security.Env().MongoURI))
	if err != nil {
		return errors.New("[mongo] mongo db 서버와의 연결 실패 err: " + err.Error())
	}
	m.initConn(mgm.Coll(&models.Message{}))
	return nil
}
// mongoconn 인스턴스에 연결된 컬렉션 할당
func (m *MongoConn) initConn(c *mgm.Collection) {
	m.Messages = c
}*/
