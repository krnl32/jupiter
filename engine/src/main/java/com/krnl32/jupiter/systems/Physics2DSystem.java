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
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
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

			// Register with Physics World (Maybe listen for EntityCreatedEvent?)
			if (rigidBody2D.rawBody == null) {
				// Create Raw Body
				BodyDef bodyDef = new BodyDef();
				bodyDef.type = switch (rigidBody2D.bodyType) {
					case STATIC -> BodyType.STATIC;
					case DYNAMIC -> BodyType.DYNAMIC;
					case KINEMATIC -> BodyType.KINEMATIC;
				};
				bodyDef.userData = entity;
				bodyDef.position.set(transform.translation.x, transform.translation.y);
				bodyDef.angle = transform.rotation.z;
				bodyDef.linearVelocity.set(rigidBody2D.initialVelocity.x, rigidBody2D.initialVelocity.y);
				bodyDef.linearDamping = rigidBody2D.linearDamping;
				bodyDef.angularDamping = rigidBody2D.angularDamping;
				bodyDef.fixedRotation = rigidBody2D.fixedRotation;
				bodyDef.bullet = rigidBody2D.continuousCollision;
				bodyDef.gravityScale = rigidBody2D.gravityScale;
				rigidBody2D.rawBody = physics2D.getWorld().createBody(bodyDef);
				rigidBody2D.rawBody.m_mass = rigidBody2D.mass;

				// Setup Colliders
				CircleCollider2DComponent circleCollider2D = entity.getComponent(CircleCollider2DComponent.class);
				if (circleCollider2D != null) {
					CircleShape circleShape = new CircleShape();
					circleShape.setRadius(circleCollider2D.radius);
					circleShape.m_p.set(circleCollider2D.offset.x, circleCollider2D.offset.y);

					FixtureDef fixtureDef = new FixtureDef();
					fixtureDef.shape = circleShape;
					fixtureDef.userData = entity;
					fixtureDef.friction = circleCollider2D.friction;
					fixtureDef.density = circleCollider2D.density;
					fixtureDef.isSensor = circleCollider2D.sensor;
					rigidBody2D.rawBody.createFixture(fixtureDef);
				}

				BoxCollider2DComponent boxCollider2D = entity.getComponent(BoxCollider2DComponent.class);
				if (boxCollider2D != null) {
					PolygonShape boxShape = new PolygonShape();
					Vector2f halfSize = boxCollider2D.getHalfSize();
					boxShape.setAsBox(halfSize.x, halfSize.y, new Vec2(boxCollider2D.offset.x, boxCollider2D.offset.y), 0);

					FixtureDef fixtureDef = new FixtureDef();
					fixtureDef.shape = boxShape;
					fixtureDef.userData = entity;
					fixtureDef.friction = boxCollider2D.friction;
					fixtureDef.density = boxCollider2D.density;
					fixtureDef.isSensor = boxCollider2D.sensor;
					rigidBody2D.rawBody.createFixture(fixtureDef);
				}
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
