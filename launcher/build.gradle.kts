import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	application
	id("com.gradleup.shadow") version "9.2.0"
}

application {
	mainClass.set("com.krnl32.jupiter.launcher.Main")
}

dependencies {
	implementation(project(":engine"))
}

tasks.named<ShadowJar>("shadowJar") {
	archiveBaseName.set("jupiter-launcher")
	archiveVersion.set("")
	archiveClassifier.set("")
}
