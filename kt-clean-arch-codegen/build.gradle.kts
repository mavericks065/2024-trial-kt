import org.hidetake.gradle.swagger.generator.GenerateSwaggerCode
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption

plugins {
	kotlin("jvm") version "1.8.0"
	kotlin("plugin.spring") version "1.8.0"
	kotlin("plugin.jpa") version "1.8.0"
	id("org.springframework.boot") version "3.0.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.hidetake.swagger.generator") version "2.18.2"
}


group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21
val openApiGeneratorVersion = "6.1.0"
val openApiGeneratorCliJar = "openapi-generator-cli-$openApiGeneratorVersion.jar"
val openApiGeneratorCliUrl = "https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/$openApiGeneratorVersion/$openApiGeneratorCliJar"
val openApiGeneratorCliPath = "$buildDir/libs/openapi-generator-cli.jar"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
	implementation("io.swagger.parser.v3:swagger-parser-v3:2.0.31")
	swaggerCodegen("org.openapitools:openapi-generator-cli:7.6.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks {
	val processResources by getting {
		dependsOn(generateSwaggerCode)
	}
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions {
		jvmTarget = "21" // Set the JVM target to a supported version
	}
}

tasks.register("downloadOpenApiGeneratorCli") {
	val outputFile = file(openApiGeneratorCliPath)
	outputs.file(outputFile)

	doLast {
		if (!outputFile.exists()) {
			println("Downloading $openApiGeneratorCliJar")
			val url = URL(openApiGeneratorCliUrl)
			url.openStream().use { input ->
				Files.copy(input, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
			}
		} else {
			println("$openApiGeneratorCliJar already exists, skipping download.")
		}
	}
}


tasks.register<Exec>("validateOpenApi") {
	dependsOn("downloadOpenApiGeneratorCli")
	group = "verification"
	description = "Validates the OpenAPI specification file"

	val openApiFilePath = "${rootDir}/src/main/resources/api/stocks.yaml"

	commandLine("java", "-jar", "$buildDir/libs/openapi-generator-cli.jar", "validate", "-i", openApiFilePath)
}

swaggerSources {
	create("stocks") .apply {
		setInputFile(file("${rootDir}/src/main/resources/api/stocks.yaml"))
		code(closureOf<GenerateSwaggerCode> {
			language = "kotlin-spring"
			outputDir = file("codegen")
			configFile = file("${rootDir}/src/main/resources/api/config.json")
			components = mapOf("models" to true, "apis" to true, "supportingFiles" to "ApiUtil.kt")
			dependsOn("validateOpenApi") //first validate the yaml
		})
	}
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
	dependsOn(swaggerSources["stocks"].code)
}
sourceSets {
	getByName("main") {
		kotlin.srcDir("${swaggerSources["stocks"].code.outputDir}/src/main/kotlin")
		resources.srcDir("${swaggerSources["stocks"].code.outputDir}/src/main/resources")
	}
}