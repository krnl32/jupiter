package com.github.krnl32.jupiter.game;

import org.joml.Vector2f;

public abstract class Actor {
	private String name; // Swap with Components
	private Vector2f position;

	public Actor(String name, Vector2f position) {
		this.name = name;
		this.position = position;
	}

	public abstract void onUpdate(float dt);
	public abstract void onRender(float dt);

	public String getName() {
		return name;
	}

	public Vector2f getPosition() {
		return position;
	}
}
