plugins {
    id("java")
}

group = "com.petition.platform"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.2.3")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.4")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.3")
    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    implementation("org.xerial:sqlite-jdbc:3.45.2.0")
    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-community-dialects
    implementation("org.hibernate.orm:hibernate-community-dialects:6.5.0.CR1")
}

tasks.test {
    useJUnitPlatform()
}