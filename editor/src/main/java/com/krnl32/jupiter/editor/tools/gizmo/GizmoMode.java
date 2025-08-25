package com.krnl32.jupiter.editor.tools.gizmo;

import imgui.extension.imguizmo.flag.Mode;

public enum GizmoMode {
	LOCAL(Mode.LOCAL),
	WORLD(Mode.WORLD);

	public final int code;

	GizmoMode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
