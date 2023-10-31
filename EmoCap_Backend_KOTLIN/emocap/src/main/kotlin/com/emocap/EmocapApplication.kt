package com.emocap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
// main 클래스가 JPA Auditing(감시, 검사) 기능을 홯성화한다. DB 작용(생성, 수정 등)을 자동으로 관리할 떄 사용한다.
@EnableJpaAuditing
class EmocapApplication
fun main(args: Array<String>) {

	runApplication<EmocapApplication>(*args)
}
