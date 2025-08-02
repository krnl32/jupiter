plugins {
	application
}

application {
	mainClass.set("com.krnl32.jupiter.launcher.Main")
}

dependencies {
	implementation(project(":engine"))
}
