package com.krnl32.jupiter.engine.script;

import java.util.Objects;

public class ScriptSettings {
	private boolean hotReload;
	private boolean compile;

	public ScriptSettings() {
	}

	public ScriptSettings(boolean hotReload, boolean compile) {
		this.hotReload = hotReload;
		this.compile = compile;
	}

	public boolean isHotReload() {
		return hotReload;
	}

	public void setHotReload(boolean hotReload) {
		this.hotReload = hotReload;
	}

	public boolean isCompile() {
		return compile;
	}

	public void setCompile(boolean compile) {
		this.compile = compile;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ScriptSettings settings = (ScriptSettings) o;
		return hotReload == settings.hotReload && compile == settings.compile;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hotReload, compile);
	}

	@Override
	public String toString() {
		return "ScriptSettings{" +
			"hotReload=" + hotReload +
			", compile=" + compile +
			'}';
	}
}
