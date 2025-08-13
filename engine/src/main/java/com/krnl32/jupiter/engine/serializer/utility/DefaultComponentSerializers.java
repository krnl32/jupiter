package com.krnl32.jupiter.engine.serializer.utility;

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
import com.krnl32.jupiter.engine.serializer.ComponentSerializerRegistry;
import com.krnl32.jupiter.engine.serializer.components.effects.BlinkComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.effects.DeathEffectComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.effects.ParticleComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.gameplay.*;
import com.krnl32.jupiter.engine.serializer.components.input.KeyboardControlComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.physics.BoxCollider2DComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.physics.CircleCollider2DComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.physics.RigidBody2DComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.projectile.ProjectileComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.projectile.ProjectileEmitterComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.renderer.CameraComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.renderer.SpriteRendererComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.utility.LifetimeComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.utility.TagComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.utility.UUIDComponentSerializer;

public class DefaultComponentSerializers {
	public static void registerAll() {
		// Utility
		ComponentSerializerRegistry.register(UUIDComponent.class, new UUIDComponentSerializer());
		ComponentSerializerRegistry.register(TagComponent.class, new TagComponentSerializer());
		ComponentSerializerRegistry.register(LifetimeComponent.class, new LifetimeComponentSerializer());

		// Effects
		ComponentSerializerRegistry.register(BlinkComponent.class, new BlinkComponentSerializer());
		ComponentSerializerRegistry.register(DeathEffectComponent.class, new DeathEffectComponentSerializer());
		ComponentSerializerRegistry.register(ParticleComponent.class, new ParticleComponentSerializer());

		// Gameplay
		ComponentSerializerRegistry.register(TransformComponent.class, new TransformComponentSerializer());
		ComponentSerializerRegistry.register(HealthComponent.class, new HealthComponentSerializer());
		ComponentSerializerRegistry.register(TeamComponent.class, new TeamComponentSerializer());
		ComponentSerializerRegistry.register(MovementIntentComponent.class, new MovementIntentComponentSerializer());
		ComponentSerializerRegistry.register(ForceMovementComponent.class, new ForceMovementComponentSerializer());
		ComponentSerializerRegistry.register(ScriptComponent.class, new ScriptComponentSerializer());

		// Physics
		ComponentSerializerRegistry.register(RigidBody2DComponent.class, new RigidBody2DComponentSerializer());
		ComponentSerializerRegistry.register(BoxCollider2DComponent.class, new BoxCollider2DComponentSerializer());
		ComponentSerializerRegistry.register(CircleCollider2DComponent.class, new CircleCollider2DComponentSerializer());

		// Renderer
		ComponentSerializerRegistry.register(CameraComponent.class, new CameraComponentSerializer());
		ComponentSerializerRegistry.register(SpriteRendererComponent.class, new SpriteRendererComponentSerializer());

		// Input
		ComponentSerializerRegistry.register(KeyboardControlComponent.class, new KeyboardControlComponentSerializer());

		// Projectile
		ComponentSerializerRegistry.register(ProjectileComponent.class, new ProjectileComponentSerializer());
		ComponentSerializerRegistry.register(ProjectileEmitterComponent.class, new ProjectileEmitterComponentSerializer());
	}

	public static void unregisterAll() {
		// Utility
		ComponentSerializerRegistry.unregister(UUIDComponent.class);
		ComponentSerializerRegistry.unregister(TagComponent.class);
		ComponentSerializerRegistry.unregister(LifetimeComponent.class);

		// Effects
		ComponentSerializerRegistry.unregister(BlinkComponent.class);
		ComponentSerializerRegistry.unregister(DeathEffectComponent.class);
		ComponentSerializerRegistry.unregister(ParticleComponent.class);

		// Gameplay
		ComponentSerializerRegistry.unregister(TransformComponent.class);
		ComponentSerializerRegistry.unregister(HealthComponent.class);
		ComponentSerializerRegistry.unregister(TeamComponent.class);
		ComponentSerializerRegistry.unregister(MovementIntentComponent.class);
		ComponentSerializerRegistry.unregister(ForceMovementComponent.class);
		ComponentSerializerRegistry.unregister(ScriptComponent.class);

		// Physics
		ComponentSerializerRegistry.unregister(RigidBody2DComponent.class);
		ComponentSerializerRegistry.unregister(BoxCollider2DComponent.class);
		ComponentSerializerRegistry.unregister(CircleCollider2DComponent.class);

		// Renderer
		ComponentSerializerRegistry.unregister(CameraComponent.class);
		ComponentSerializerRegistry.unregister(SpriteRendererComponent.class);

		// Input
		ComponentSerializerRegistry.unregister(KeyboardControlComponent.class);

		// Projectile
		ComponentSerializerRegistry.unregister(ProjectileComponent.class);
		ComponentSerializerRegistry.unregister(ProjectileEmitterComponent.class);
	}
}
