plugins {
	application
}

application {
	mainClass.set("com.krnl32.jupiter.editor.Main")
}

dependencies {
	implementation(project(":engine"))
}
