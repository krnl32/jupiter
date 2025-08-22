package com.krnl32.jupiter.engine.sceneserializer.jnative.components.utility;

import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.nio.charset.StandardCharsets;

/*
 * == TagComponent (size: 1+N Bytes) ==
 * 1 Byte 	Length			uint8
 * N Bytes	Tag				UTF-8 String
 */
public class JTagComponentSerializer implements ComponentSerializer<TagComponent, byte[]> {
	@Override
	public byte[] serialize(TagComponent component) {
		byte[] tagBytes = component.tag.getBytes(StandardCharsets.UTF_8);

		if (tagBytes.length > 255) {
			Logger.error("JTagComponentSerializer Serialize failed, Invalid Tag Size > 255({})", tagBytes.length);
			return null;
		}

		byte[] data = new byte[1 + tagBytes.length];
		data[0] = (byte) tagBytes.length;
		System.arraycopy(tagBytes, 0, data, 1, tagBytes.length);

		return data;
	}

	@Override
	public TagComponent deserialize(byte[] data, EntityResolver resolver) {
		int length = Byte.toUnsignedInt(data[0]);

		if (data.length < 1 + length) {
			Logger.error("JTagComponentSerializer Deserialize failed, Corrupted Data, Size({})",data.length);
			return null;
		}

		String tag = new String(data, 1, length, StandardCharsets.UTF_8);
		return new TagComponent(tag);
	}
}
