package com.krnl32.jupiter.renderer;

public class VertexBufferAttribute {
	private String name;
	private int size;
	private ShaderDataType type;
	private boolean normalized;
	private int offset;

	public VertexBufferAttribute(String name, int size, ShaderDataType type, boolean normalized, int offset) {
		this.name = name;
		this.size = size;
		this.type = type;
		this.normalized = normalized;
		this.offset = offset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ShaderDataType getType() {
		return type;
	}

	public void setType(ShaderDataType type) {
		this.type = type;
	}

	public boolean isNormalized() {
		return normalized;
	}

	public void setNormalized(boolean normalized) {
		this.normalized = normalized;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
