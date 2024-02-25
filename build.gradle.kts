plugins {
	java
	war
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
	// {
    //     exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    // }
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.postgresql:postgresql")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("javax.xml.bind:jaxb-api:2.3.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("javax.transaction:javax.transaction-api:1.3")
	compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
