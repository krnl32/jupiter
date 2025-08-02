import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.named

plugins {
	application
	id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
	mainClass.set("com.krnl32.jupiter.editor.Main")
}

dependencies {
	implementation(project(":engine"))
}

tasks.named<ShadowJar>("shadowJar") {
	archiveBaseName.set("editor")
	archiveVersion.set("")
	//archiveClassifier.set("")
}
