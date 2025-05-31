package com.github.krnl32.jupiter.game;

import com.github.krnl32.jupiter.actors.EmptyActor;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
	private List<Actor> actors = new ArrayList<>();

	public void onUpdate(float dt) {
		for (var actor: actors)
			actor.onUpdate(dt);
	}

	public void onRender(float dt) {
		for (var actor: actors)
			actor.onRender(dt);
	}

	public abstract void load();

	public void addActor(Actor actor) {
		actors.add(actor);
	}

	public void spawnActor() {
		actors.add(new EmptyActor());
	}
}
