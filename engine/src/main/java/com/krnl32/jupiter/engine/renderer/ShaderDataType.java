package com.krnl32.jupiter.engine.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_BOOL;

public enum ShaderDataType {
	Bool(GL_BOOL, 1),
	Byte(GL_BYTE, 1),
	Short(GL_SHORT, 2),
	UShort(GL_UNSIGNED_SHORT, 2),
	Int(GL_INT, 4),
	UInt(GL_UNSIGNED_INT, 4),
	Float(GL_FLOAT, 4),
	Double(GL_DOUBLE, 8);

	private final int nativeType;
	private final int size;

	ShaderDataType(int nativeType, int size) {
		this.nativeType = nativeType;
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public int getNativeType() {
		return nativeType;
	}
}
