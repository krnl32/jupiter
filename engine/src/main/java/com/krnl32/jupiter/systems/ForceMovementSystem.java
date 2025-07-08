package com.krnl32.jupiter.systems;

import com.krnl32.jupiter.components.ForceMovementComponent;
import com.krnl32.jupiter.components.MovementIntentComponent;
import com.krnl32.jupiter.components.RigidBody2DComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.Renderer;
import org.jbox2d.common.Vec2;

public class ForceMovementSystem implements System {
	public final Registry registry;

	public ForceMovementSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(ForceMovementComponent.class, RigidBody2DComponent.class, MovementIntentComponent.class)) {
			ForceMovementComponent forceMovement = entity.getComponent(ForceMovementComponent.class);
			RigidBody2DComponent rigidBody2D = entity.getComponent(RigidBody2DComponent.class);
			MovementIntentComponent movementIntent = entity.getComponent(MovementIntentComponent.class);

			if (rigidBody2D.rawBody == null)
				continue;

			float forceScale = movementIntent.sprint ? forceMovement.sprintMultiplier : 1.0f;

			// Handle Movement
			if (movementIntent.translation.lengthSquared() > 0.001f) {
				Vec2 force = new Vec2(
					movementIntent.translation.x * forceMovement.moveForce * forceScale,
					movementIntent.translation.y * forceMovement.moveForce * forceScale
				);
				rigidBody2D.rawBody.applyForceToCenter(force);
			}

			// Clamp Max Speed
			Vec2 velocity = rigidBody2D.rawBody.getLinearVelocity();
			float speed = velocity.length();
			float maxSpeed = forceMovement.maxSpeed * forceScale;
			if (speed > maxSpeed) {
				velocity.mulLocal(maxSpeed / speed);
				rigidBody2D.rawBody.setLinearVelocity(velocity);
			}

			// Rotation
			if (movementIntent.rotation.z != 0.0f) {
				float torque = movementIntent.rotation.z * forceMovement.rotationTorque;
				rigidBody2D.rawBody.applyTorque(torque);
			}

			// Jump
			if (movementIntent.jump) {
				rigidBody2D.rawBody.applyLinearImpulse(new Vec2(0, forceMovement.jumpImpulse), rigidBody2D.rawBody.getWorldCenter());
				movementIntent.jump = false;
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {

	}
}
