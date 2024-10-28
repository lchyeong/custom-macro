plugins {
    java
    application
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "com.custommacro"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

javafx {
    version = "23"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.swing")
}
application {
    mainClass.set("com.custommacro.custommacro.Launcher")
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.openjfx:javafx-controls:23")
    implementation("org.openjfx:javafx-fxml:23")
    implementation("org.openjfx:javafx-swing:23")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.custommacro.custommacro.Launcher"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}