package com.krnl32.jupiter.engine.serializer.components.input;

import com.krnl32.jupiter.engine.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;

import java.util.Map;

public class KeyboardControlComponentSerializer implements ComponentSerializer<KeyboardControlComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(KeyboardControlComponent component) {
		return Map.of(
			"upKey", component.upKey.getCode(),
			"downKey", component.downKey.getCode(),
			"forwardKey", component.forwardKey.getCode(),
			"backwardKey", component.backwardKey.getCode(),
			"leftKey", component.leftKey.getCode(),
			"rightKey", component.rightKey.getCode(),
			"rotateLeftKey", component.rotateLeftKey.getCode(),
			"rotateRightKey", component.rotateRightKey.getCode(),
			"jumpKey", component.jumpKey.getCode(),
			"sprintKey", component.sprintKey.getCode()
		);
	}

	@Override
	public KeyboardControlComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new KeyboardControlComponent(
			KeyCode.fromCode((int) data.get("upKey")),
			KeyCode.fromCode((int) data.get("downKey")),
			KeyCode.fromCode((int) data.get("forwardKey")),
			KeyCode.fromCode((int) data.get("backwardKey")),
			KeyCode.fromCode((int) data.get("leftKey")),
			KeyCode.fromCode((int) data.get("rightKey")),
			KeyCode.fromCode((int) data.get("rotateLeftKey")),
			KeyCode.fromCode((int) data.get("rotateRightKey")),
			KeyCode.fromCode((int) data.get("jumpKey")),
			KeyCode.fromCode((int) data.get("sprintKey"))
		);
	}
}
