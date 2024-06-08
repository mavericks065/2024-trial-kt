import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("org.springframework.boot") version "3.3.1-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.openapi.generator") version "7.5.0"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com.nig"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	developmentOnly("org.openapitools:openapi-generator-gradle-plugin:7.2.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
		jvmTarget = JvmTarget.JVM_21
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

openApiValidate {
	inputSpec.set(projectDir.resolve("src/main/resources/api/openapi.yml").absolutePath)
	recommend.set(true)
}

openApiGenerate {
	generatorName.set("kotlin-spring")
	inputSpec.set(projectDir.resolve("src/main/resources/api/openapi.yml").absolutePath)
	outputDir.set(projectDir.resolve(".").absolutePath)
	apiPackage.set("com.nig.stocks.application.codegen.api")
	modelPackage.set("com.nig.stocks.application.codegen.model")
	// I need to find what to change here in order to not regenerate the main function
	configOptions.set(mapOf(
		"interfaceOnly" to "true", // Generate only the interfaces
		"useTags" to "true" // Use tags for the interface names
	))
}
// not ready to be used below
tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateApi") {
	generatorName.set("kotlin-spring")
	inputSpec.set(projectDir.resolve("src/main/resources/api/openapi.yml").absolutePath)
	outputDir.set(projectDir.resolve(".").absolutePath)
	apiPackage.set("com.nig.stocks.application.codegen.api")
	modelPackage.set("com.nig.stocks.application.codegen.model")
	configOptions.set(mapOf(
		"interfaceOnly" to "true",
		"useTags" to "true"
	))
	group = "openapi tools"
	description = "Generates API files from the OpenAPI specification"
}

openApiMeta {
	generatorName.set("kotlin-spring")
	outputFolder.set("/src/main/kotlin")
	packageName.set("com.nig.stocks.application.codegen")
}
