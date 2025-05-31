package com.github.krnl32.jupiter.actors;

import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.game.Actor;
import org.joml.Vector2f;

public class EmptyActor extends Actor {
	public EmptyActor(String name, Vector2f position) {
		super(name, position);
	}

	@Override
	public void onUpdate(float dt) {
		Logger.info("Actor({}, {})", getName(), getPosition());
	}

	@Override
	public void onRender(float dt) {

	}
}
