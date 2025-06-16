package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.components.KeyboardControlComponent;
import com.github.krnl32.jupiter.input.KeyCode;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import org.json.JSONObject;

public class KeyboardControlComponentSerializer implements ComponentSerializer<KeyboardControlComponent> {
	@Override
	public JSONObject serialize(KeyboardControlComponent component) {
		return new JSONObject()
			.put("moveSpeed", component.moveSpeed)
			.put("rotationSpeed", component.rotationSpeed)
			.put("upKey", component.upKey.getCode())
			.put("downKey", component.downKey.getCode())
			.put("forwardKey", component.forwardKey.getCode())
			.put("backwardKey", component.backwardKey.getCode())
			.put("leftKey", component.leftKey.getCode())
			.put("rightKey", component.rightKey.getCode())
			.put("rotateLeftKey", component.rotateLeftKey.getCode())
			.put("rotateRightKey", component.rotateRightKey.getCode());
	}

	@Override
	public KeyboardControlComponent deserialize(JSONObject data) {
		return new KeyboardControlComponent(
			data.getFloat("moveSpeed"),
			data.getFloat("rotationSpeed"),
			KeyCode.fromCode(data.getInt("upKey")),
			KeyCode.fromCode(data.getInt("downKey")),
			KeyCode.fromCode(data.getInt("forwardKey")),
			KeyCode.fromCode(data.getInt("backwardKey")),
			KeyCode.fromCode(data.getInt("leftKey")),
			KeyCode.fromCode(data.getInt("rightKey")),
			KeyCode.fromCode(data.getInt("rotateLeftKey")),
			KeyCode.fromCode(data.getInt("rotateRightKey")));
	}
}
