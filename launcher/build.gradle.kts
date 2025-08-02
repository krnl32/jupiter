import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	application
	id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
	mainClass.set("com.krnl32.jupiter.launcher.Main")
}

dependencies {
	implementation(project(":engine"))
}

tasks.named<ShadowJar>("shadowJar") {
	archiveBaseName.set("launcher")
	archiveVersion.set("")
	//archiveClassifier.set("")
}
