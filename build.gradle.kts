plugins {
}

group = "com.krnl32"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

subprojects {
	apply(plugin = "java-library")

	group = rootProject.group
	version = rootProject.version

	the<JavaPluginExtension>().apply {
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}

	repositories {
		mavenCentral()
	}
}
