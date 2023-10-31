package utils

import "github.com/gorilla/websocket"

// slice에서 value의 값을 삭제하고 리턴한다.
func DeleteSliceValue[T *websocket.Conn | int | string](slice []T, value T) []T {

	newSlice := []T{}

	for _, v := range slice {
		if v != value {
			newSlice = append(newSlice, v)
		}
	}

	return newSlice
}

// slice에 value가 존재하지 않을 경우 true를 리턴한다.
func UnExistSliceValue[T *websocket.Conn | int | string](slice []T, value T) bool {

	flag := false

	for _, v := range slice {
		if v == value {
			flag = true
			break
		}
	}

	return flag
}
