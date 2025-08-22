package com.krnl32.jupiter.engine.sceneserializer;

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
import com.krnl32.jupiter.engine.sceneserializer.data.components.effects.DataBlinkComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.effects.DataDeathEffectComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.effects.DataParticleComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.gameplay.*;
import com.krnl32.jupiter.engine.sceneserializer.data.components.input.DataKeyboardControlComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.physics.DataBoxCollider2DComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.physics.DataCircleCollider2DComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.physics.DataRigidBody2DComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.projectile.DataProjectileComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.projectile.DataProjectileEmitterComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.renderer.DataCameraComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.renderer.DataSpriteRendererComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.utility.DataLifetimeComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.utility.DataTagComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.data.components.utility.DataUUIDComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.components.gameplay.JTransformComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.components.utility.JTagComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.components.utility.JUUIDComponentSerializer;

public class ComponentSerializerRegistryFactory {
	public static ComponentSerializerRegistry createDataComponentSerializerRegistry() {
		ComponentSerializerRegistry serializerRegistry = new ComponentSerializerRegistry();

		// Utility
		serializerRegistry.register(UUIDComponent.class, new DataUUIDComponentSerializer());
		serializerRegistry.register(TagComponent.class, new DataTagComponentSerializer());
		serializerRegistry.register(LifetimeComponent.class, new DataLifetimeComponentSerializer());

		// Effects
		serializerRegistry.register(BlinkComponent.class, new DataBlinkComponentSerializer());
		serializerRegistry.register(DeathEffectComponent.class, new DataDeathEffectComponentSerializer());
		serializerRegistry.register(ParticleComponent.class, new DataParticleComponentSerializer());

		// Gameplay
		serializerRegistry.register(TransformComponent.class, new DataTransformComponentSerializer());
		serializerRegistry.register(HealthComponent.class, new DataHealthComponentSerializer());
		serializerRegistry.register(TeamComponent.class, new DataTeamComponentSerializer());
		serializerRegistry.register(MovementIntentComponent.class, new DataMovementIntentComponentSerializer());
		serializerRegistry.register(ForceMovementComponent.class, new DataForceMovementComponentSerializer());
		serializerRegistry.register(ScriptComponent.class, new DataScriptComponentSerializer());

		// Physics
		serializerRegistry.register(RigidBody2DComponent.class, new DataRigidBody2DComponentSerializer());
		serializerRegistry.register(BoxCollider2DComponent.class, new DataBoxCollider2DComponentSerializer());
		serializerRegistry.register(CircleCollider2DComponent.class, new DataCircleCollider2DComponentSerializer());

		// Renderer
		serializerRegistry.register(CameraComponent.class, new DataCameraComponentSerializer());
		serializerRegistry.register(SpriteRendererComponent.class, new DataSpriteRendererComponentSerializer());

		// Input
		serializerRegistry.register(KeyboardControlComponent.class, new DataKeyboardControlComponentSerializer());

		// Projectile
		serializerRegistry.register(ProjectileComponent.class, new DataProjectileComponentSerializer());
		serializerRegistry.register(ProjectileEmitterComponent.class, new DataProjectileEmitterComponentSerializer());

		return serializerRegistry;
	}

	public static ComponentSerializerRegistry createJComponentSerializerRegistry() {
		ComponentSerializerRegistry serializerRegistry = new ComponentSerializerRegistry();

		// Utility
		serializerRegistry.register(UUIDComponent.class, new JUUIDComponentSerializer());
		serializerRegistry.register(TagComponent.class, new JTagComponentSerializer());
//		serializerRegistry.register(LifetimeComponent.class, new DataLifetimeComponentSerializer());

		// Effects
//		serializerRegistry.register(BlinkComponent.class, new DataBlinkComponentSerializer());
//		serializerRegistry.register(DeathEffectComponent.class, new DataDeathEffectComponentSerializer());
//		serializerRegistry.register(ParticleComponent.class, new DataParticleComponentSerializer());

		// Gameplay
		serializerRegistry.register(TransformComponent.class, new JTransformComponentSerializer());
//		serializerRegistry.register(HealthComponent.class, new DataHealthComponentSerializer());
//		serializerRegistry.register(TeamComponent.class, new DataTeamComponentSerializer());
//		serializerRegistry.register(MovementIntentComponent.class, new DataMovementIntentComponentSerializer());
//		serializerRegistry.register(ForceMovementComponent.class, new DataForceMovementComponentSerializer());
//		serializerRegistry.register(ScriptComponent.class, new DataScriptComponentSerializer());

		// Physics
//		serializerRegistry.register(RigidBody2DComponent.class, new DataRigidBody2DComponentSerializer());
//		serializerRegistry.register(BoxCollider2DComponent.class, new DataBoxCollider2DComponentSerializer());
//		serializerRegistry.register(CircleCollider2DComponent.class, new DataCircleCollider2DComponentSerializer());

		// Renderer
//		serializerRegistry.register(CameraComponent.class, new DataCameraComponentSerializer());
//		serializerRegistry.register(SpriteRendererComponent.class, new DataSpriteRendererComponentSerializer());

		// Input
//		serializerRegistry.register(KeyboardControlComponent.class, new DataKeyboardControlComponentSerializer());

		// Projectile
//		serializerRegistry.register(ProjectileComponent.class, new DataProjectileComponentSerializer());
//		serializerRegistry.register(ProjectileEmitterComponent.class, new DataProjectileEmitterComponentSerializer());

		return serializerRegistry;
	}
}
