package com.krnl32.jupiter.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VertexBufferLayout {
	private final List<VertexBufferAttribute> attributes;
	private int stride;

	public VertexBufferLayout(VertexBufferAttribute... attributes) {
		this.attributes = new ArrayList<>(Arrays.asList(attributes));
		calculate();
	}

	public List<VertexBufferAttribute> getAttributes() {
		return attributes;
	}

	public int getStride() {
		return stride;
	}

	private void calculate() {
		int offset = 0;
		for(var attrib: attributes) {
			attrib.setOffset(offset);
			offset += attrib.getSize() * attrib.getType().getSize();
			stride += attrib.getSize() * attrib.getType().getSize();
		}
	}
}
