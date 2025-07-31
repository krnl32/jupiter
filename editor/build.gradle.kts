import org.gradle.internal.classpath.Instrumented.systemProperty

plugins {
	application
}

application {
	mainClass.set("com.krnl32.jupiter.editor.Main")
	applicationDefaultJvmArgs = listOf("-Dproject.resource=$projectDir/src/main/resources")
}

dependencies {
	implementation(project(":engine"))
}
