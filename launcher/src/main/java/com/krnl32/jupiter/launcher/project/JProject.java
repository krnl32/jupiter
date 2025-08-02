package com.krnl32.jupiter.launcher.project;

import java.util.Objects;

public final class JProject {
	private final String name;
	private final String path;
	private final String engineVersion;
	private final String template;

	public JProject(String name, String path, String engineVersion, String template) {
		this.name = name;
		this.path = path;
		this.engineVersion = engineVersion;
		this.template = template;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getEngineVersion() {
		return engineVersion;
	}

	public String getTemplate() {
		return template;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		JProject jProject = (JProject) o;
		return Objects.equals(name, jProject.name) && Objects.equals(path, jProject.path) && Objects.equals(engineVersion, jProject.engineVersion) && Objects.equals(template, jProject.template);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, path, engineVersion, template);
	}

	@Override
	public String toString() {
		return "JProject{" +
			"name='" + name + '\'' +
			", path='" + path + '\'' +
			", engineVersion='" + engineVersion + '\'' +
			", template='" + template + '\'' +
			'}';
	}
}
