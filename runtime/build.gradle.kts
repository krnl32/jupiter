import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.named

version = "0.0.1"

plugins {
	application
	id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
	mainClass.set("com.krnl32.jupiter.runtime.Main")
}

dependencies {
	implementation(project(":engine"))
}

tasks.named<ShadowJar>("shadowJar") {
	archiveBaseName.set("jupiter-runtime")
	archiveVersion.set("$version")
	archiveClassifier.set("")
}
