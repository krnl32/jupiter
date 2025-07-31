plugins {
	application
}

application {
	mainClass.set("com.krnl32.jupiter.launcher.Main")
	applicationDefaultJvmArgs = listOf("-Dproject.resource=$projectDir/src/main/resources")
}

dependencies {
	implementation(project(":engine"))
}
