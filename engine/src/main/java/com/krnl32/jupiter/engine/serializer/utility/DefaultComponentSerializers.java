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
import com.krnl32.jupiter.engine.serializer.EntitySerializerRegistry;
import com.krnl32.jupiter.engine.serializer.oldcomponents.effects.BlinkComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.effects.DeathEffectComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.effects.ParticleComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.gameplay.*;
import com.krnl32.jupiter.engine.serializer.oldcomponents.input.KeyboardControlComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.physics.BoxCollider2DComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.physics.CircleCollider2DComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.physics.RigidBody2DComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.projectile.ProjectileComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.projectile.ProjectileEmitterComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.renderer.CameraComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.renderer.SpriteRendererComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.utility.LifetimeComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.utility.TagComponentSerializer;
import com.krnl32.jupiter.engine.serializer.oldcomponents.utility.UUIDComponentSerializer;

public class DefaultComponentSerializers {
	public static void registerAll() {
		// Utility
		EntitySerializerRegistry.registerComponentSerializer(UUIDComponent.class, new UUIDComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(TagComponent.class, new TagComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(LifetimeComponent.class, new LifetimeComponentSerializer());

		// Effects
		EntitySerializerRegistry.registerComponentSerializer(BlinkComponent.class, new BlinkComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(DeathEffectComponent.class, new DeathEffectComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(ParticleComponent.class, new ParticleComponentSerializer());

		// Gameplay
		EntitySerializerRegistry.registerComponentSerializer(TransformComponent.class, new TransformComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(HealthComponent.class, new HealthComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(TeamComponent.class, new TeamComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(MovementIntentComponent.class, new MovementIntentComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(ForceMovementComponent.class, new ForceMovementComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(ScriptComponent.class, new ScriptComponentSerializer());

		// Physics
		EntitySerializerRegistry.registerComponentSerializer(RigidBody2DComponent.class, new RigidBody2DComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(BoxCollider2DComponent.class, new BoxCollider2DComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(CircleCollider2DComponent.class, new CircleCollider2DComponentSerializer());

		// Renderer
		EntitySerializerRegistry.registerComponentSerializer(CameraComponent.class, new CameraComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(SpriteRendererComponent.class, new SpriteRendererComponentSerializer());

		// Input
		EntitySerializerRegistry.registerComponentSerializer(KeyboardControlComponent.class, new KeyboardControlComponentSerializer());

		// Projectile
		EntitySerializerRegistry.registerComponentSerializer(ProjectileComponent.class, new ProjectileComponentSerializer());
		EntitySerializerRegistry.registerComponentSerializer(ProjectileEmitterComponent.class, new ProjectileEmitterComponentSerializer());
	}
}
