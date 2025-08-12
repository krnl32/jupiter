package com.krnl32.jupiter.engine.cloner.utility;

import com.krnl32.jupiter.engine.cloner.ComponentClonerRegistry;
import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.engine.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.engine.components.effects.ParticleComponent;
import com.krnl32.jupiter.engine.components.gameplay.*;
import com.krnl32.jupiter.engine.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.engine.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.components.utility.LifetimeComponent;
import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.model.Sprite;
import com.krnl32.jupiter.engine.renderer.Camera;
import com.krnl32.jupiter.engine.renderer.ProjectionType;
import com.krnl32.jupiter.engine.script.ScriptInstance;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class DefaultComponentCloners {
	public static void registerAll() {
		// Utility
		ComponentClonerRegistry.register(UUIDComponent.class, component -> new UUIDComponent(component.uuid));
		ComponentClonerRegistry.register(TagComponent.class, component -> new TagComponent(component.tag));
		ComponentClonerRegistry.register(LifetimeComponent.class, component -> new LifetimeComponent(component.remainingTime));

		// Effects
		ComponentClonerRegistry.register(BlinkComponent.class, component -> new BlinkComponent(
			component.duration,
			component.interval,
			component.elapsedTime,
			component.blinkTime,
			component.visible
		));
		ComponentClonerRegistry.register(DeathEffectComponent.class, component -> new DeathEffectComponent(
			component.particleCount,
			new Sprite(
				component.particleSprite.getIndex(),
				new Vector4f(component.particleSprite.getColor()),
				component.particleSprite.getTextureAssetId(),
				component.particleSprite.getTextureUV().clone()
			)
		));
		ComponentClonerRegistry.register(ParticleComponent.class, component -> new ParticleComponent(
			new Vector3f(component.velocity),
			component.duration,
			component.remainingTime
		));

		// Gameplay
		ComponentClonerRegistry.register(TransformComponent.class, component -> new TransformComponent(
			new Vector3f(component.translation),
			new Vector3f(component.rotation),
			new Vector3f(component.scale)
		));
		ComponentClonerRegistry.register(HealthComponent.class, component -> new HealthComponent(
			component.maxHealth,
			component.currentHealth
		));
		ComponentClonerRegistry.register(TeamComponent.class, component -> new TeamComponent(component.teamId));
		ComponentClonerRegistry.register(MovementIntentComponent.class, component -> new MovementIntentComponent(
			new Vector3f(component.translation),
			new Vector3f(component.rotation),
			component.jump,
			component.sprint
		));
		ComponentClonerRegistry.register(ForceMovementComponent.class, component -> new ForceMovementComponent(
			component.moveForce,
			component.sprintMultiplier,
			component.maxSpeed,
			component.rotationTorque,
			component.jumpImpulse
		));
		ComponentClonerRegistry.register(ScriptComponent.class, component -> {
			List<ScriptInstance> scripts = new ArrayList<>();
			for (ScriptInstance script : component.scripts) {
				scripts.add(new ScriptInstance(script.getScriptAssetId(), script.isDisabled()));
			}
			return new ScriptComponent(scripts);
		});

		// Physics
		ComponentClonerRegistry.register(RigidBody2DComponent.class, component -> new RigidBody2DComponent(
			component.bodyType,
			new Vector2f(component.velocity),
			component.linearDamping,
			component.angularDamping,
			component.mass,
			component.gravityScale,
			component.fixedRotation,
			component.continuousCollision
		));
		ComponentClonerRegistry.register(BoxCollider2DComponent.class, component -> new BoxCollider2DComponent(
			new Vector2f(component.size),
			new Vector2f(component.offset),
			component.friction,
			component.density,
			component.sensor
		));
		ComponentClonerRegistry.register(CircleCollider2DComponent.class, component -> new CircleCollider2DComponent(
			component.radius,
			new Vector2f(component.offset),
			component.friction,
			component.density,
			component.sensor
		));

		// Renderer
		ComponentClonerRegistry.register(CameraComponent.class, component -> {
			Camera camera = new Camera(
				component.camera.getPosition(),
				component.camera.getWorldUp(),
				component.camera.getYaw(),
				component.camera.getPitch(),
				component.camera.getRoll(),
				component.camera.getZoom(),
				component.camera.getTurnSpeed(),
				component.camera.getRollSpeed(),
				component.camera.getZoomSpeed(),
				component.camera.isMouseEnabled()
			);

			float projectionNear = camera.getProjectionNear();
			float projectionFar = camera.getProjectionFar();

			if (component.camera.getProjectionType() == ProjectionType.ORTHOGRAPHIC) {
				float projectionSize = component.camera.getProjectionSize();
				camera.setOrthographic(projectionSize, projectionNear, projectionFar);
			}
			else if(component.camera.getProjectionType() == ProjectionType.PERSPECTIVE) {
				float projectionFOV = component.camera.getProjectionFOV();
				camera.setPerspective(projectionFOV, projectionNear, projectionFar);
			}

			return new CameraComponent(camera, component.primary);
		});
		ComponentClonerRegistry.register(SpriteRendererComponent.class, component -> new SpriteRendererComponent(
			component.index,
			new Vector4f(component.color),
			component.textureAssetId,
			component.textureUV.clone()
		));

		// Input
		ComponentClonerRegistry.register(KeyboardControlComponent.class, component -> new KeyboardControlComponent(
			component.upKey,
			component.downKey,
			component.forwardKey,
			component.backwardKey,
			component.leftKey,
			component.rightKey,
			component.rotateLeftKey,
			component.rotateRightKey,
			component.jumpKey,
			component.sprintKey
		));

		// Projectile
		ComponentClonerRegistry.register(ProjectileComponent.class, component -> new ProjectileComponent(
			new Entity(
				component.owner.getId(),
				component.owner.getRegistry()
			),
			component.damage,
			component.canHitOwner
		));
		ComponentClonerRegistry.register(ProjectileEmitterComponent.class, component -> new ProjectileEmitterComponent(
			component.shootKey,
			component.fireRate,
			component.projectileSpeed,
			new Sprite(
				component.sprite.getIndex(),
				new Vector4f(component.sprite.getColor()),
				component.sprite.getTextureAssetId(),
				component.sprite.getTextureUV().clone()
			)
		));
	}

	public static void unregisterAll() {
		// Utility
		ComponentClonerRegistry.unregister(UUIDComponent.class);
		ComponentClonerRegistry.unregister(TagComponent.class);
		ComponentClonerRegistry.unregister(LifetimeComponent.class);

		// Effects
		ComponentClonerRegistry.unregister(BlinkComponent.class);
		ComponentClonerRegistry.unregister(DeathEffectComponent.class);
		ComponentClonerRegistry.unregister(ParticleComponent.class);

		// Gameplay
		ComponentClonerRegistry.unregister(TransformComponent.class);
		ComponentClonerRegistry.unregister(HealthComponent.class);
		ComponentClonerRegistry.unregister(TeamComponent.class);
		ComponentClonerRegistry.unregister(MovementIntentComponent.class);
		ComponentClonerRegistry.unregister(ForceMovementComponent.class);
		ComponentClonerRegistry.unregister(ScriptComponent.class);

		// Physics
		ComponentClonerRegistry.unregister(RigidBody2DComponent.class);
		ComponentClonerRegistry.unregister(BoxCollider2DComponent.class);
		ComponentClonerRegistry.unregister(CircleCollider2DComponent.class);

		// Renderer
		ComponentClonerRegistry.unregister(CameraComponent.class);
		ComponentClonerRegistry.unregister(SpriteRendererComponent.class);

		// Input
		ComponentClonerRegistry.unregister(KeyboardControlComponent.class);

		// Projectile
		ComponentClonerRegistry.unregister(ProjectileComponent.class);
		ComponentClonerRegistry.unregister(ProjectileEmitterComponent.class);
	}
}
