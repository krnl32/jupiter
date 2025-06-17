package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.components.BoxColliderComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.event.EventBus;
import com.github.krnl32.jupiter.events.physics.CollisionEvent;
import com.github.krnl32.jupiter.renderer.Renderer;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class CollisionSystem implements System {
	private final Registry registry;

	public CollisionSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		List<Entity> entities = new ArrayList<>(registry.getEntitiesWith(BoxColliderComponent.class, TransformComponent.class));

		for (int i = 0; i < entities.size(); i++) {
			Entity entityA = entities.get(i);
			BoxColliderComponent entityABoxCollider = entityA.getComponent(BoxColliderComponent.class);
			TransformComponent entityATransform = entityA.getComponent(TransformComponent.class);

			for (int j = i + 1; j < entities.size(); j++) {
				Entity entityB = entities.get(j);
				BoxColliderComponent entityBBoxCollider = entityB.getComponent(BoxColliderComponent.class);
				TransformComponent entityBTransform = entityB.getComponent(TransformComponent.class);

				if (checkAABBCollision(entityATransform, entityABoxCollider, entityBTransform, entityBBoxCollider)) {
					EventBus.getInstance().emit(new CollisionEvent(entityA, entityB));
					//Logger.info("Entity({}) Collided with Entity({})", entityA.getTagOrId(), entityB.getTagOrId());
				}
			}
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}

	private boolean checkAABBCollision(TransformComponent transformA, BoxColliderComponent boxColliderA,
									   TransformComponent transformB, BoxColliderComponent boxColliderB) {
		Vector3f entityAColliderSize = new Vector3f(
			boxColliderA.size.x * transformA.scale.x,
			boxColliderA.size.y * transformA.scale.y,
			boxColliderA.size.z * transformA.scale.z
		);
		Vector3f entityAHalfSize = new Vector3f(entityAColliderSize).mul(0.5f);

		Vector3f entityBColliderSize = new Vector3f(
			boxColliderB.size.x * transformB.scale.x,
			boxColliderB.size.y * transformB.scale.y,
			boxColliderB.size.z * transformB.scale.z
		);
		Vector3f entityBHalfSize = new Vector3f(entityBColliderSize).mul(0.5f);

		float ax = transformA.translation.x + boxColliderA.offset.x * transformA.scale.x;
		float ay = transformA.translation.y + boxColliderA.offset.y * transformA.scale.y;
		float bx = transformB.translation.x + boxColliderB.offset.x * transformB.scale.x;
		float by = transformB.translation.y + boxColliderB.offset.y * transformB.scale.y;

		return Math.abs(ax - bx) < (entityAHalfSize.x + entityBHalfSize.x) &&
			Math.abs(ay - by) < (entityAHalfSize.y + entityBHalfSize.y);
	}
}
