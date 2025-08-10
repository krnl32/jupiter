val lwjglVersion = "3.3.6"
val jomlVersion = "1.10.7"
val lwjglNatives = "natives-windows"
val imguiVersion = "1.89.0"

dependencies {
	// JUnit
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")

	// LWJGL
	implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
	api(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

	implementation("org.lwjgl:lwjgl")
	implementation("org.lwjgl:lwjgl-assimp")
	api("org.lwjgl:lwjgl-glfw")
	implementation("org.lwjgl:lwjgl-nfd")
	implementation("org.lwjgl:lwjgl-openal")
	implementation("org.lwjgl:lwjgl-opengl")
	implementation("org.lwjgl:lwjgl-stb")

	runtimeOnly("org.lwjgl:lwjgl::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-assimp::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-glfw::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-nfd::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-openal::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-opengl::$lwjglNatives")
	runtimeOnly("org.lwjgl:lwjgl-stb::$lwjglNatives")

	// ImGui
	api("io.github.spair:imgui-java-binding:$imguiVersion")
	api("io.github.spair:imgui-java-lwjgl3:$imguiVersion")
	api("io.github.spair:imgui-java-${lwjglNatives}:${imguiVersion}")

	// Others
	implementation("ch.qos.logback:logback-classic:1.5.18")
	api("org.luaj:luaj-jse:3.0.1")
	api("org.joml:joml:$jomlVersion")
	api("org.json:json:20250517")
	api("commons-cli:commons-cli:1.9.0")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")

	// Thirdparty
	implementation(files("${rootProject.projectDir}/thirdparty/jbox2d-library.jar"))
}

tasks.test {
	useJUnitPlatform()
}
