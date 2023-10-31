package security

import (
	"os"
)

// env file structure
type myEnvs struct {
	MongoURI      string
	MongoDBNAME   string
	KotlinAddress string
}

var env myEnvs

// init a env structure
func InitEnv() error {
	env.MongoURI = os.Getenv("MONGOURI")
	env.MongoDBNAME = os.Getenv("MONGODBNAME")
	env.KotlinAddress = os.Getenv("KOTLINADDRES")

	return nil
}

// get a env structure
func Env() myEnvs {
	return env
}
