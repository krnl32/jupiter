package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.ecs.Component;
import com.github.krnl32.jupiter.input.KeyCode;

public class KeyboardMovementComponent implements Component {
	public float moveSpeed;
	public KeyCode upKey, downKey, forwardKey, backwardKey, leftKey, rightKey;

	public KeyboardMovementComponent(float moveSpeed, KeyCode upKey, KeyCode downKey, KeyCode forwardKey, KeyCode backwardKey, KeyCode leftKey, KeyCode rightKey) {
		this.moveSpeed = moveSpeed;
		this.upKey = upKey;
		this.downKey = downKey;
		this.forwardKey = forwardKey;
		this.backwardKey = backwardKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}
}
