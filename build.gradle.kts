plugins {
	java
	id("org.springframework.boot") version "3.0.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("io.freefair.lombok") version "8.4"
}

group = "com.brigada"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("javax.xml.bind:jaxb-api:2.3.0")
	implementation("org.apache.activemq:activemq-client:6.1.1")
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("com.ibm.mq:com.ibm.mq.allclient:9.3.5.0")
	implementation("org.springframework:spring-jms:6.1.5")
	implementation("jakarta.jms:jakarta.jms-api:3.1.0")
	implementation("com.rabbitmq.jms:rabbitmq-jms:3.2.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
