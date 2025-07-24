package com.krnl32.jupiter.serializer.utility;

import com.krnl32.jupiter.components.effects.BlinkComponent;
import com.krnl32.jupiter.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.components.effects.ParticleComponent;
import com.krnl32.jupiter.components.gameplay.*;
import com.krnl32.jupiter.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.components.renderer.CameraComponent;
import com.krnl32.jupiter.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.components.utility.LifetimeComponent;
import com.krnl32.jupiter.components.utility.TagComponent;
import com.krnl32.jupiter.components.utility.UUIDComponent;
import com.krnl32.jupiter.serializer.SerializerRegistry;
import com.krnl32.jupiter.serializer.components.effects.BlinkComponentSerializer;
import com.krnl32.jupiter.serializer.components.effects.DeathEffectComponentSerializer;
import com.krnl32.jupiter.serializer.components.effects.ParticleComponentSerializer;
import com.krnl32.jupiter.serializer.components.gameplay.*;
import com.krnl32.jupiter.serializer.components.input.KeyboardControlComponentSerializer;
import com.krnl32.jupiter.serializer.components.physics.BoxCollider2DComponentSerializer;
import com.krnl32.jupiter.serializer.components.physics.CircleCollider2DComponentSerializer;
import com.krnl32.jupiter.serializer.components.physics.RigidBody2DComponentSerializer;
import com.krnl32.jupiter.serializer.components.projectile.ProjectileComponentSerializer;
import com.krnl32.jupiter.serializer.components.projectile.ProjectileEmitterComponentSerializer;
import com.krnl32.jupiter.serializer.components.renderer.CameraComponentSerializer;
import com.krnl32.jupiter.serializer.components.renderer.SpriteRendererComponentSerializer;
import com.krnl32.jupiter.serializer.components.utility.LifetimeComponentSerializer;
import com.krnl32.jupiter.serializer.components.utility.TagComponentSerializer;
import com.krnl32.jupiter.serializer.components.utility.UUIDComponentSerializer;

public class DefaultComponentSerializers {
	public static void registerAll() {
		// Utility
		SerializerRegistry.registerComponentSerializer(UUIDComponent.class, new UUIDComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TagComponent.class, new TagComponentSerializer());
		SerializerRegistry.registerComponentSerializer(LifetimeComponent.class, new LifetimeComponentSerializer());

		// Effects
		SerializerRegistry.registerComponentSerializer(BlinkComponent.class, new BlinkComponentSerializer());
		SerializerRegistry.registerComponentSerializer(DeathEffectComponent.class, new DeathEffectComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ParticleComponent.class, new ParticleComponentSerializer());

		// Gameplay
		SerializerRegistry.registerComponentSerializer(TransformComponent.class, new TransformComponentSerializer());
		SerializerRegistry.registerComponentSerializer(HealthComponent.class, new HealthComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TeamComponent.class, new TeamComponentSerializer());
		SerializerRegistry.registerComponentSerializer(MovementIntentComponent.class, new MovementIntentComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ForceMovementComponent.class, new ForceMovementComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ScriptComponent.class, new ScriptComponentSerializer());

		// Physics
		SerializerRegistry.registerComponentSerializer(RigidBody2DComponent.class, new RigidBody2DComponentSerializer());
		SerializerRegistry.registerComponentSerializer(BoxCollider2DComponent.class, new BoxCollider2DComponentSerializer());
		SerializerRegistry.registerComponentSerializer(CircleCollider2DComponent.class, new CircleCollider2DComponentSerializer());

		// Renderer
		SerializerRegistry.registerComponentSerializer(CameraComponent.class, new CameraComponentSerializer());
		SerializerRegistry.registerComponentSerializer(SpriteRendererComponent.class, new SpriteRendererComponentSerializer());

		// Input
		SerializerRegistry.registerComponentSerializer(KeyboardControlComponent.class, new KeyboardControlComponentSerializer());

		// Projectile
		SerializerRegistry.registerComponentSerializer(ProjectileComponent.class, new ProjectileComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ProjectileEmitterComponent.class, new ProjectileEmitterComponentSerializer());
	}
}
