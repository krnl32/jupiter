package com.krnl32.jupiter.engine.sceneserializer;

public enum ComponentType {
	INVALID(0x0),

	// Utility
	UUIDComponent(0x01),
	TagComponent(0x02),
	LifeTimeComponent(0x03),

	// Effects
	BlinkComponent(0x04),
	DeathEffectComponent(0x05),
	ParticleComponent(0x06),

	// Gameplay
	TransformComponent(0x07),
	HealthComponent(0x08),
	TeamComponent(0x09),
	MovementIntentComponent(0x0A),
	ForceMovementComponent(0x0B),
	ScriptComponent(0x0C),

	// Physics
	RigidBody2DComponent(0x0D),
	BoxCollider2DComponent(0x0E),
	CircleCollider2DComponent(0x0F),

	// Renderer
	CameraComponent(0x10),
	SpriteRendererComponent(0x11),

	// Input
	KeyboardControlComponent(0x12),

	// Projectile
	ProjectileComponent(0x13),
	ProjectileEmitterComponent(0x14);

	private final int id;

	ComponentType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static ComponentType fromId(int id) {
		for (ComponentType type : values()) {
			if (type.id == id) {
				return type;
			}
		}
		return INVALID;
	}
}
