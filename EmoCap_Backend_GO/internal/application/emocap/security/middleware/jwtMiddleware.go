package middleware

import (
	"emocap/internal/infrastructure/grpc/service"
	"net/http"

	"github.com/labstack/echo/v4"
)

// jwt인증 미들웨어
func JWTMiddleware(next echo.HandlerFunc) echo.HandlerFunc {
	return func(c echo.Context) error {
		if service.ValidateToken(c.Request().Header.Get("token")) {
			return c.String(http.StatusOK, "인증된 유저")
		}
		return c.String(http.StatusUnauthorized, "인증되지 않은 유저")
	}
}
