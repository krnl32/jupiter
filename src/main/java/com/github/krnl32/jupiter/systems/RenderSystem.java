package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.components.SpriteRendererComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.renderer.RenderSpriteCommand;
import com.github.krnl32.jupiter.renderer.Renderer;
import com.github.krnl32.jupiter.renderer.Sprite;

public class RenderSystem implements System {
	private final Registry registry;

	public RenderSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		for (Entity entity: registry.getEntitiesWith(TransformComponent.class, SpriteRendererComponent.class)) {
			TransformComponent transform = entity.getComponent(TransformComponent.class);
			SpriteRendererComponent spriteRenderer = entity.getComponent(SpriteRendererComponent.class);
			Sprite spriteRender = new Sprite((int)(transform.scale.x * spriteRenderer.sprite.getWidth()), (int)(transform.scale.y * spriteRenderer.sprite.getHeight()), spriteRenderer.sprite.getIndex(), spriteRenderer.sprite.getColor(), spriteRenderer.sprite.getTextureAssetID());

			renderer.submit(new RenderSpriteCommand(transform.translation, spriteRender));
		}
	}
}
