plugins {
	application
}

application {
	mainClass.set("com.krnl32.jupiter.Main")
}

dependencies {
	implementation(project(":engine"))
}
