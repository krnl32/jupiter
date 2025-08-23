package com.krnl32.jupiter.engine.physics;

public enum BodyType {
	STATIC((byte) 0x01),
	DYNAMIC((byte) 0x02),
	KINEMATIC((byte) 0x03);

	private final byte id;

	BodyType(byte id) {
		this.id = id;
	}

	public byte getId() {
		return id;
	}

	public static BodyType fromId(byte id) {
		for (BodyType type : values()) {
			if (type.id == id) {
				return type;
			}
		}
		return null;
	}
}
