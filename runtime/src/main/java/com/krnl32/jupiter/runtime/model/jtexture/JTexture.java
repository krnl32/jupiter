package com.krnl32.jupiter.runtime.model.jtexture;

import java.util.Arrays;
import java.util.Objects;

public class JTexture {
	private final JTextureHeader header;
	private final byte[] data;

	public JTexture(JTextureHeader header, byte[] data) {
		this.header = header;
		this.data = data;
	}

	public JTextureHeader getHeader() {
		return header;
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		JTexture jTexture = (JTexture) o;
		return Objects.equals(header, jTexture.header) && Objects.deepEquals(data, jTexture.data);
	}

	@Override
	public int hashCode() {
		return Objects.hash(header, Arrays.hashCode(data));
	}

	@Override
	public String toString() {
		return "JTexture{" +
			"header=" + header +
			'}';
	}
}
