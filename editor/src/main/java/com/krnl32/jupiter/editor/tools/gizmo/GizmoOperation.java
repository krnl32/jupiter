package com.krnl32.jupiter.editor.tools.gizmo;

import imgui.extension.imguizmo.flag.Operation;

public enum GizmoOperation {
	TRANSLATE(Operation.TRANSLATE),
	ROTATE(Operation.ROTATE),
	SCALE(Operation.SCALE),
	BOUNDS(Operation.BOUNDS),
	UNIVERSAL(Operation.UNIVERSAL);

	private final int code;

	GizmoOperation(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
