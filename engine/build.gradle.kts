val lwjglVersion = "3.3.6"
val jomlVersion = "1.10.7"
val lwjglNatives = "natives-windows"
val imguiVersion = "1.89.0"

dependencies {
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")

	implementation("ch.qos.logback:logback-classic:1.5.18")
	api("org.json:json:20250517")

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

	api("org.joml:joml:$jomlVersion")
	api("io.github.spair:imgui-java-binding:$imguiVersion")
	api("io.github.spair:imgui-java-lwjgl3:$imguiVersion")
	api("io.github.spair:imgui-java-${lwjglNatives}:${imguiVersion}")
}

tasks.test {
	useJUnitPlatform()
}
