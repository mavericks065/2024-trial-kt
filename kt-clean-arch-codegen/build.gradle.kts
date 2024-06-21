import org.hidetake.gradle.swagger.generator.GenerateSwaggerCode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	id("org.springframework.boot") version "3.3.1-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.5"
	id ("org.hidetake.swagger.generator") version "2.19.2"
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

	//open api code generation
	swaggerCodegen("org.openapitools:openapi-generator-cli:7.3.0")
	implementation ("io.swagger.core.v3:swagger-annotations:2.2.20")

	//open api ui
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

tasks {
	val processResources by getting {
		dependsOn(generateSwaggerCode)
	}
}

swaggerSources {
	create("stocks") .apply {
		setInputFile(file("${rootDir}/src/main/resources/api/stocks.yaml"))
		code(closureOf<GenerateSwaggerCode> {
			language = "kotlin-spring"
			outputDir = file("codegen")
			configFile = file("${rootDir}/src/main/resources/api/config.json")
			components = mapOf("models" to true, "apis" to true, "supportingFiles" to "ApiUtil.kt")
			dependsOn("validation") //first validate the yaml
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
//val stocks by swaggerSources.creating {
//	inputFile.set(file("${rootDir}/src/main/resources/api/stocks.yaml"))
//	code {
//		language.set("spring")
//		configFile.set(file("${rootDir}/src/main/resources/api/config.json"))
//		components.set(mapOf("models" to true, "apis" to true, "supportingFiles" to "ApiUtil.kt"))
//		dependsOn("validation") //first validate the yaml
//	}
//}
