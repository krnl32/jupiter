import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.named

version = "0.0.1"

plugins {
	application
	id("com.gradleup.shadow") version "9.2.0"
}

application {
	mainClass.set("com.krnl32.jupiter.editor.Main")
}

dependencies {
	implementation(project(":engine"))

	implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
}

tasks.named<ShadowJar>("shadowJar") {
	archiveBaseName.set("jupiter-editor")
	archiveVersion.set("$version")
	archiveClassifier.set("")
}
