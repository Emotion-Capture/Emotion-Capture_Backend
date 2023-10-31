import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
	// grpc
	id("com.google.protobuf") version "0.8.19"
	idea
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

val protobufVersion = "3.24.0"
val javaGrpcVersion = "1.58.0"
val grpcKotlinVersion = "1.0.0"

val snippetsDir by extra {file("build/generated-snippets")}
extra["springCloudGcpVersion"] = "4.7.2"
extra["springCloudVersion"] = "2022.0.4"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web-services")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	// https://mvnrepository.com/artifact/mysql/mysql-connector-java
	implementation("mysql:mysql-connector-java:8.0.28")
	// https://mvnrepository.com/artifact/org.springframework/spring-jdbc
	implementation("org.springframework:spring-jdbc:6.0.11")
	// https://mvnrepository.com/artifact/org.modelmapper/modelmapper
	implementation("org.modelmapper:modelmapper:3.1.1")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	// https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.0")
	// https://mvnrepository.com/artifact/javax.annotation/javax.annotation-api
	implementation("javax.annotation:javax.annotation-api:1.2")
	// grpc
	// https://mvnrepository.com/artifact/com.google.protobuf/protobuf-kotlin
	implementation("com.google.protobuf:protobuf-kotlin:${protobufVersion}")
	// https://mvnrepository.com/artifact/io.grpc/grpc-protobuf
	implementation("io.grpc:grpc-protobuf:${javaGrpcVersion}")
	// https://mvnrepository.com/artifact/io.grpc/grpc-kotlin-stub
	implementation("io.grpc:grpc-kotlin-stub:${grpcKotlinVersion}")
	// https://mvnrepository.com/artifact/net.devh/grpc-server-spring-boot-autoconfigure
	implementation("net.devh:grpc-server-spring-boot-autoconfigure:2.15.0.RELEASE")
	/*implementation("com.google.cloud:spring-cloud-gcp-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("org.springframework.security:spring-security-test")*/
}

dependencyManagement {
	imports {
		mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:${property("springCloudGcpVersion")}")
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	outputs.dir(snippetsDir)
}

tasks.asciidoctor {
	inputs.dir(snippetsDir)
	dependsOn(tasks.test)
}

// grpc
sourceSets {
	getByName("main") {
		java {
			srcDirs (
				"build/generated/source/proto/main/java",
				"build/generated/source/proto/main/kotlin"
			)
		}
	}
}

repositories {
	mavenCentral()
	maven("https://maven.pkg.jetbrains.space/public/p/kotlinx/kotlinx-html/maven")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:$protobufVersion"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:$javaGrpcVersion"
		}
		id("grpckt") {
//			artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk7@jar"
			artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk7@jar"
		}
	}
//	generatedFilesBaseDir = "${projectDir}/src/"
	generateProtoTasks {
		all().forEach { generateProtoTask ->
			generateProtoTask.plugins {
				id("grpc")
				id("grpckt")
			}
			generateProtoTask.builtins {
				id("kotlin")
			}
		}
	}
}

// 에러 해결 ---> Entry google/protobuf/any.proto is a duplicate but no duplicate handling strategy has been set.
tasks {
	withType<Copy> {
		filesMatching("**/*.proto") {
			duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.EXCLUDE
		}
	}
}
