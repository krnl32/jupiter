package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.game.Component;
import com.github.krnl32.jupiter.input.Input;
import com.github.krnl32.jupiter.input.KeyCode;
import com.github.krnl32.jupiter.renderer.Renderer;

public class MovementComponent extends Component {
	private final float moveSpeed;
	private final KeyCode upKey, downKey, forwardKey, backwardKey, leftKey, rightKey;

	public MovementComponent(float moveSpeed, KeyCode upKey, KeyCode downKey, KeyCode forwardKey, KeyCode backwardKey, KeyCode leftKey, KeyCode rightKey) {
		this.moveSpeed = moveSpeed;
		this.upKey = upKey;
		this.downKey = downKey;
		this.forwardKey = forwardKey;
		this.backwardKey = backwardKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}

	@Override
	public void onUpdate(float dt) {
		TransformComponent transform = getGameObject().getComponent(TransformComponent.class);

		float velocity = moveSpeed * dt;
		if (Input.getInstance().isKeyDown(upKey))
			transform.getTranslation().y += velocity;
		else if (Input.getInstance().isKeyDown(downKey))
			transform.getTranslation().y -= velocity;
		else if (Input.getInstance().isKeyDown(forwardKey))
			transform.getTranslation().z -= velocity;
		else if(Input.getInstance().isKeyDown(backwardKey))
			transform.getTranslation().z += velocity;
		else if(Input.getInstance().isKeyDown(leftKey))
			transform.getTranslation().x -= velocity;
		else if(Input.getInstance().isKeyDown(rightKey))
			transform.getTranslation().x += velocity;
	}

	@Override
	public void onRender(float dt, Renderer renderer) {

	}
}
