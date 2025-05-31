package com.github.krnl32.jupiter.game;

public abstract class Component {
	private Actor actor;

	public abstract void onUpdate(float dt);
	public abstract void onRender(float dt);

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}
}
