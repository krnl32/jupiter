package com.krnl32.jupiter.serializer.components.input;

import com.krnl32.jupiter.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.input.KeyCode;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public class KeyboardControlComponentSerializer implements ComponentSerializer<KeyboardControlComponent> {
	@Override
	public JSONObject serialize(KeyboardControlComponent component) {
		return new JSONObject()
			.put("upKey", component.upKey.getCode())
			.put("downKey", component.downKey.getCode())
			.put("forwardKey", component.forwardKey.getCode())
			.put("backwardKey", component.backwardKey.getCode())
			.put("leftKey", component.leftKey.getCode())
			.put("rightKey", component.rightKey.getCode())
			.put("rotateLeftKey", component.rotateLeftKey.getCode())
			.put("rotateRightKey", component.rotateRightKey.getCode())
			.put("jumpKey", component.jumpKey.getCode())
			.put("sprintKey", component.sprintKey.getCode());
	}

	@Override
	public KeyboardControlComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new KeyboardControlComponent(
			KeyCode.fromCode(data.getInt("upKey")),
			KeyCode.fromCode(data.getInt("downKey")),
			KeyCode.fromCode(data.getInt("forwardKey")),
			KeyCode.fromCode(data.getInt("backwardKey")),
			KeyCode.fromCode(data.getInt("leftKey")),
			KeyCode.fromCode(data.getInt("rightKey")),
			KeyCode.fromCode(data.getInt("rotateLeftKey")),
			KeyCode.fromCode(data.getInt("rotateRightKey")),
			KeyCode.fromCode(data.getInt("jumpKey")),
			KeyCode.fromCode(data.getInt("sprintKey")));
	}

	@Override
	public KeyboardControlComponent clone(KeyboardControlComponent component) {
		return new KeyboardControlComponent(
			component.upKey,
			component.downKey,
			component.forwardKey,
			component.backwardKey,
			component.leftKey,
			component.rightKey,
			component.rotateLeftKey,
			component.rotateRightKey,
			component.jumpKey,
			component.sprintKey
		);
	}
}
