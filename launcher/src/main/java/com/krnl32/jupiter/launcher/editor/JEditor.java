package com.krnl32.jupiter.launcher.editor;

import java.util.Objects;

public final class JEditor {
	private final String path;
	private final String version;

	public JEditor(String path, String version) {
		this.path = path;
		this.version = version;
	}

	public String getPath() {
		return path;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		JEditor editor = (JEditor) o;
		return Objects.equals(path, editor.path) && Objects.equals(version, editor.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, version);
	}

	@Override
	public String toString() {
		return "JEditor{" +
			"path='" + path + '\'' +
			", version='" + version + '\'' +
			'}';
	}
}
