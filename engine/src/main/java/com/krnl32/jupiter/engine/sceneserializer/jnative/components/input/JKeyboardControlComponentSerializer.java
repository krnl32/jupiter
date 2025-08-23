package com.krnl32.jupiter.engine.sceneserializer.jnative.components.input;

import com.krnl32.jupiter.engine.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == KeyboardControlComponent (size: 40 Bytes) ==
 * 4 Bytes	UpKey			uint32
 * 4 Bytes	DownKey			uint32
 * 4 Bytes	ForwardKey		uint32
 * 4 Bytes	BackwardKey		uint32
 * 4 Bytes	LeftKey			uint32
 * 4 Bytes	RightKey		uint32
 * 4 Bytes	rotateLeftKey	uint32
 * 4 Bytes	rotateRightKey	uint32
 * 4 Bytes	jumpKey			uint32
 * 4 Bytes	SprintKey		uint32
 */
public class JKeyboardControlComponentSerializer implements ComponentSerializer<KeyboardControlComponent, byte[]> {
	private static final int SIZE = 40;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(KeyboardControlComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeInt(buffer, component.upKey.getCode());
		JSerializerUtility.serializeInt(buffer, component.downKey.getCode());
		JSerializerUtility.serializeInt(buffer, component.forwardKey.getCode());
		JSerializerUtility.serializeInt(buffer, component.backwardKey.getCode());
		JSerializerUtility.serializeInt(buffer, component.leftKey.getCode());
		JSerializerUtility.serializeInt(buffer, component.rightKey.getCode());
		JSerializerUtility.serializeInt(buffer, component.rotateLeftKey.getCode());
		JSerializerUtility.serializeInt(buffer, component.rotateRightKey.getCode());
		JSerializerUtility.serializeInt(buffer, component.jumpKey.getCode());
		JSerializerUtility.serializeInt(buffer, component.sprintKey.getCode());

		return buffer.array();
	}

	@Override
	public KeyboardControlComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JKeyboardControlComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		int upKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode upKey = KeyCode.fromCode(upKeyCode);

		int downKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode downKey = KeyCode.fromCode(downKeyCode);

		int forwardKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode forwardKey = KeyCode.fromCode(forwardKeyCode);

		int backwardKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode backwardKey = KeyCode.fromCode(backwardKeyCode);

		int leftKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode leftKey = KeyCode.fromCode(leftKeyCode);

		int rightKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode rightKey = KeyCode.fromCode(rightKeyCode);

		int rotateLeftKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode rotateLeftKey = KeyCode.fromCode(rotateLeftKeyCode);

		int rotateRightKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode rotateRightKey = KeyCode.fromCode(rotateRightKeyCode);

		int jumpKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode jumpKey = KeyCode.fromCode(jumpKeyCode);

		int sprintKeyCode = JSerializerUtility.deserializeInt(buffer);
		KeyCode sprintKey = KeyCode.fromCode(sprintKeyCode);

		return new KeyboardControlComponent(
			upKey, downKey,
			forwardKey, backwardKey,
			leftKey, rightKey,
			rotateLeftKey, rotateRightKey,
			jumpKey, sprintKey
		);
	}
}
