package com.krnl32.jupiter.engine.systems.physics;

import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.entity.EntityDestroyedEvent;
import com.krnl32.jupiter.engine.physics.CollisionRule;
import com.krnl32.jupiter.engine.physics.DelegatingContactFilter;
import com.krnl32.jupiter.engine.physics.Physics2D;
import com.krnl32.jupiter.engine.renderer.Renderer;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.joml.Vector2f;

import java.util.HashSet;
import java.util.Set;

public class Physics2DSystem implements System {
	private final Registry registry;
	private final Physics2D physics2D;
	private final Set<Body> bodiesToDestroy;

	public Physics2DSystem(Registry registry) {
		this.registry = registry;
		this.physics2D = new Physics2D();
		this.physics2D.getWorld().setContactFilter(new DelegatingContactFilter(((entityA, entityB) -> true)));
		this.bodiesToDestroy = new HashSet<>();

		EventBus.getInstance().register(EntityDestroyedEvent.class, event -> {
			RigidBody2DComponent rigidBody2D = event.getEntity().getComponent(RigidBody2DComponent.class);
			if (rigidBody2D != null && rigidBody2D.rawBody != null) {
				bodiesToDestroy.add(rigidBody2D.rawBody);
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
				createPhysicsBody(entity, rigidBody2D, transform);
			}

			// ReSync Physics Body
			if (rigidBody2D.rawBody != null && rigidBody2D.resync) {
				resyncBody(rigidBody2D);
				rigidBody2D.resync = false;
			}

			// Update Physics
			if (rigidBody2D.rawBody != null) {
				var rawBodyPosition = rigidBody2D.rawBody.getPosition();
				transform.translation.set(rawBodyPosition.x, rawBodyPosition.y, transform.translation.z);
				transform.rotation.set(transform.rotation.x, transform.rotation.y, rigidBody2D.rawBody.getAngle());
			}
		}

		physics2D.onUpdate(dt);

		// Handle Destruction
		for (Body body : bodiesToDestroy) {
			if (body.m_world != null) {
				physics2D.getWorld().destroyBody(body);
			}
		}
		bodiesToDestroy.clear();
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}

	public void setCollisionRule(CollisionRule collisionRule) {
		this.physics2D.getWorld().setContactFilter(new DelegatingContactFilter(collisionRule));
	}

	private void createPhysicsBody(Entity entity, RigidBody2DComponent rigidBody2D, TransformComponent transform) {
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
		bodyDef.linearVelocity.set(rigidBody2D.velocity.x, rigidBody2D.velocity.y);
		bodyDef.linearDamping = rigidBody2D.linearDamping;
		bodyDef.angularDamping = rigidBody2D.angularDamping;
		bodyDef.fixedRotation = rigidBody2D.fixedRotation;
		bodyDef.bullet = rigidBody2D.continuousCollision;
		bodyDef.gravityScale = rigidBody2D.gravityScale;
		rigidBody2D.rawBody = physics2D.getWorld().createBody(bodyDef);

		addFixtures(entity, rigidBody2D);
		rigidBody2D.rawBody.resetMassData();
	}

	private void addFixtures(Entity entity, RigidBody2DComponent rigidBody2D) {
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

	private void resyncBody(RigidBody2DComponent rigidBody2D) {
		Body body = rigidBody2D.rawBody;

		BodyType newBodyType = switch (rigidBody2D.bodyType) {
			case STATIC -> BodyType.STATIC;
			case DYNAMIC -> BodyType.DYNAMIC;
			case KINEMATIC -> BodyType.KINEMATIC;
		};
		if (body.getType() != newBodyType) {
			body.setType(newBodyType);
		}

		body.setLinearDamping(rigidBody2D.linearDamping);
		body.setAngularDamping(rigidBody2D.angularDamping);
		body.setFixedRotation(rigidBody2D.fixedRotation);
		body.setBullet(rigidBody2D.continuousCollision);
		body.setGravityScale(rigidBody2D.gravityScale);

		for (var fixture = body.getFixtureList(); fixture != null; fixture = fixture.getNext()) {
			fixture.setDensity(rigidBody2D.mass);
		}
		body.resetMassData();
	}
}
