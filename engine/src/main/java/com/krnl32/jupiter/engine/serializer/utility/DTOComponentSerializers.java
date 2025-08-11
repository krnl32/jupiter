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
import com.krnl32.jupiter.engine.serializer.components.effects.DTOBlinkComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.effects.DTODeathEffectComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.effects.DTOParticleComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.gameplay.*;
import com.krnl32.jupiter.engine.serializer.components.input.DTOKeyboardControlComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.physics.DTOBoxCollider2DComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.physics.DTOCircleCollider2DComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.physics.DTORigidBody2DComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.projectile.DTOProjectileComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.projectile.DTOProjectileEmitterComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.renderer.DTOCameraComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.renderer.DTOSpriteRendererComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.utility.DTOLifetimeComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.utility.DTOTagComponentSerializer;
import com.krnl32.jupiter.engine.serializer.components.utility.DTOUUIDComponentSerializer;

// Maybe Remove this File?
public class DTOComponentSerializers {
	public static void registerAll() {
		// Utility
		ComponentSerializerRegistry.register(UUIDComponent.class, new DTOUUIDComponentSerializer());
		ComponentSerializerRegistry.register(TagComponent.class, new DTOTagComponentSerializer());
		ComponentSerializerRegistry.register(LifetimeComponent.class, new DTOLifetimeComponentSerializer());

		// Effects
		ComponentSerializerRegistry.register(BlinkComponent.class, new DTOBlinkComponentSerializer());
		ComponentSerializerRegistry.register(DeathEffectComponent.class, new DTODeathEffectComponentSerializer());
		ComponentSerializerRegistry.register(ParticleComponent.class, new DTOParticleComponentSerializer());

		// Gameplay
		ComponentSerializerRegistry.register(TransformComponent.class, new DTOTransformComponentSerializer());
		ComponentSerializerRegistry.register(HealthComponent.class, new DTOHealthComponentSerializer());
		ComponentSerializerRegistry.register(TeamComponent.class, new DTOTeamComponentSerializer());
		ComponentSerializerRegistry.register(MovementIntentComponent.class, new DTOMovementIntentComponentSerializer());
		ComponentSerializerRegistry.register(ForceMovementComponent.class, new DTOForceMovementComponentSerializer());
		ComponentSerializerRegistry.register(ScriptComponent.class, new DTOScriptComponentSerializer());

		// Physics
		ComponentSerializerRegistry.register(RigidBody2DComponent.class, new DTORigidBody2DComponentSerializer());
		ComponentSerializerRegistry.register(BoxCollider2DComponent.class, new DTOBoxCollider2DComponentSerializer());
		ComponentSerializerRegistry.register(CircleCollider2DComponent.class, new DTOCircleCollider2DComponentSerializer());

		// Renderer
		ComponentSerializerRegistry.register(CameraComponent.class, new DTOCameraComponentSerializer());
		ComponentSerializerRegistry.register(SpriteRendererComponent.class, new DTOSpriteRendererComponentSerializer());

		// Input
		ComponentSerializerRegistry.register(KeyboardControlComponent.class, new DTOKeyboardControlComponentSerializer());

		// Projectile
		ComponentSerializerRegistry.register(ProjectileComponent.class, new DTOProjectileComponentSerializer());
		ComponentSerializerRegistry.register(ProjectileEmitterComponent.class, new DTOProjectileEmitterComponentSerializer());
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
