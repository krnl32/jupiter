package com.krnl32.jupiter.components;

import com.krnl32.jupiter.ecs.Component;
import com.krnl32.jupiter.input.KeyCode;

public class KeyboardControlComponent implements Component {
	public float moveSpeed, rotationSpeed;
	public KeyCode upKey, downKey, forwardKey, backwardKey, leftKey, rightKey;
	public KeyCode rotateLeftKey, rotateRightKey;
	public KeyCode jumpKey;
	public KeyCode sprintKey;

	public KeyboardControlComponent(float moveSpeed, float rotationSpeed, KeyCode upKey, KeyCode downKey, KeyCode forwardKey, KeyCode backwardKey, KeyCode leftKey, KeyCode rightKey, KeyCode rotateLeftKey, KeyCode rotateRightKey, KeyCode jumpKey, KeyCode sprintKey) {
		this.moveSpeed = moveSpeed;
		this.rotationSpeed = rotationSpeed;
		this.upKey = upKey;
		this.downKey = downKey;
		this.forwardKey = forwardKey;
		this.backwardKey = backwardKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.rotateLeftKey = rotateLeftKey;
		this.rotateRightKey = rotateRightKey;
		this.jumpKey = jumpKey;
		this.sprintKey = sprintKey;
	}
}
