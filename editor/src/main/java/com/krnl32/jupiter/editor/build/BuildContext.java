package com.krnl32.jupiter.editor.build;

import java.nio.file.Path;

public class BuildContext {
	private BuildSettings settings;
	private Path outputDirectory;

	public BuildSettings getSettings() {
		return settings;
	}

	public void setSettings(BuildSettings settings) {
		this.settings = settings;
	}

	public Path getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(Path outputDirectory) {
		this.outputDirectory = outputDirectory;
	}
}
