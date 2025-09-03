package com.krnl32.jupiter.engine.renderer;

public enum ProjectionType {
	ORTHOGRAPHIC((byte) 0x01),
	PERSPECTIVE((byte) 0x02);

	private final byte id;

	ProjectionType(byte id) {
		this.id = id;
	}

	public byte getId() {
		return id;
	}

	public static ProjectionType fromId(byte id) {
		for (ProjectionType type : values()) {
			if (type.id == id) {
				return type;
			}
		}

		return null;
	}
};
