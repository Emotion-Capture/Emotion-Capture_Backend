package response

type CommonResponse[T any] struct {
	Status  int
	Success bool
	Message string
	Data    T
}
