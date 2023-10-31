package com.emocap.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "emocap API",
        description = "emotion capture 의 API 명세서",
        version = "0.1"
    )
)
@Configuration
class SwaggerConfig {

}

