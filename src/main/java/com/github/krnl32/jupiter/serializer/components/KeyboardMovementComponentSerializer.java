package com.github.krnl32.jupiter.serializer.components;

import com.github.krnl32.jupiter.components.KeyboardMovementComponent;
import com.github.krnl32.jupiter.input.KeyCode;
import com.github.krnl32.jupiter.serializer.ComponentSerializer;
import org.json.JSONObject;

public class KeyboardMovementComponentSerializer implements ComponentSerializer<KeyboardMovementComponent> {
	@Override
	public JSONObject serialize(KeyboardMovementComponent component) {
		return new JSONObject()
			.put("moveSpeed", component.moveSpeed)
			.put("upKey", component.upKey.getCode())
			.put("downKey", component.downKey.getCode())
			.put("forwardKey", component.forwardKey.getCode())
			.put("backwardKey", component.backwardKey.getCode())
			.put("leftKey", component.leftKey.getCode())
			.put("rightKey", component.rightKey.getCode());
	}

	@Override
	public KeyboardMovementComponent deserialize(JSONObject data) {
		return new KeyboardMovementComponent(
			data.getFloat("moveSpeed"),
			KeyCode.fromCode(data.getInt("upKey")),
			KeyCode.fromCode(data.getInt("downKey")),
			KeyCode.fromCode(data.getInt("forwardKey")),
			KeyCode.fromCode(data.getInt("backwardKey")),
			KeyCode.fromCode(data.getInt("leftKey")),
			KeyCode.fromCode(data.getInt("rightKey")));
	}
}
