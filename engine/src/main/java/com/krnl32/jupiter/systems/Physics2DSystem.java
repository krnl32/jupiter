package com.krnl32.jupiter.systems;

import com.krnl32.jupiter.components.BoxCollider2DComponent;
import com.krnl32.jupiter.components.CircleCollider2DComponent;
import com.krnl32.jupiter.components.RigidBody2DComponent;
import com.krnl32.jupiter.components.TransformComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.entity.EntityDestroyedEvent;
import com.krnl32.jupiter.physics.Physics2D;
import com.krnl32.jupiter.renderer.Renderer;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.joml.Vector2f;

public class Physics2DSystem implements System {
	private final Registry registry;
	private final Physics2D physics2D;

	public Physics2DSystem(Registry registry) {
		this.registry = registry;
		this.physics2D = new Physics2D();

		EventBus.getInstance().register(EntityDestroyedEvent.class, event -> {
			RigidBody2DComponent rigidBody2D = event.getEntity().getComponent(RigidBody2DComponent.class);
			if (rigidBody2D != null) {
				physics2D.getWorld().destroyBody(rigidBody2D.rawBody);
				rigidBody2D.rawBody = null;
			}
		});
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(RigidBody2DComponent.class, TransformComponent.class)) {
			RigidBody2DComponent rigidBody2D = entity.getComponent(RigidBody2DComponent.class);
			TransformComponent transform = entity.getComponent(TransformComponent.class);

			// Register with Physics World
			if (rigidBody2D.rawBody == null) {
				BodyDef bodyDef = new BodyDef();
				bodyDef.angle = transform.rotation.z;
				bodyDef.position.set(transform.translation.x, transform.translation.y);
				bodyDef.angularDamping = rigidBody2D.angularDamping;
				bodyDef.linearDamping = rigidBody2D.linearDamping;
				bodyDef.fixedRotation = rigidBody2D.fixedRotation;
				bodyDef.bullet = rigidBody2D.continuousCollision;

				bodyDef.type = switch (rigidBody2D.bodyType) {
					case STATIC -> BodyType.STATIC;
					case DYNAMIC -> BodyType.DYNAMIC;
					case KINEMATIC -> BodyType.KINEMATIC;
				};

				PolygonShape shape = new PolygonShape();

				CircleCollider2DComponent circleCollider2D = entity.getComponent(CircleCollider2DComponent.class);
				BoxCollider2DComponent boxCollider2D = entity.getComponent(BoxCollider2DComponent.class);

				if (circleCollider2D != null) {
					shape.setRadius(circleCollider2D.radius);
				} else if (boxCollider2D != null) {
					Vector2f halfSize = boxCollider2D.getHalfSize();
					Vector2f offset = boxCollider2D.offset;
					shape.setAsBox(halfSize.x, halfSize.y, new Vec2(offset.x, offset.y), 0);

					Vec2 bodyDefPos = bodyDef.position;
					bodyDef.position.set((bodyDefPos.x + offset.x), (bodyDefPos.y + offset.y));
				}

				rigidBody2D.rawBody = physics2D.getWorld().createBody(bodyDef);
				rigidBody2D.rawBody.createFixture(shape, rigidBody2D.mass);
			}

			// Update Physics
			if (rigidBody2D.rawBody != null) {
				var rawBodyPosition = rigidBody2D.rawBody.getPosition();
				transform.translation.set(rawBodyPosition.x, rawBodyPosition.y, transform.translation.z);
				transform.rotation.set(transform.rotation.x, transform.rotation.y, rigidBody2D.rawBody.getAngle());
			}
		}

		physics2D.onUpdate(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
