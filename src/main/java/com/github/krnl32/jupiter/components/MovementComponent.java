package com.github.krnl32.jupiter.components;

import com.github.krnl32.jupiter.game.Component;
import com.github.krnl32.jupiter.renderer.Renderer;

public class MovementComponent extends Component {
	@Override
	public void onUpdate(float dt) {
		TransformComponent transform = getGameObject().getComponent(TransformComponent.class);
		RigidBodyComponent rigidBody = getGameObject().getComponent(RigidBodyComponent.class);
		transform.getTranslation().add(rigidBody.getVelocity().x * dt, rigidBody.getVelocity().y * dt, rigidBody.getVelocity().z * dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {

	}
}
