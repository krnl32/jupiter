package com.github.krnl32.jupiter.systems;

import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.TextureAsset;
import com.github.krnl32.jupiter.components.SpriteRendererComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.ecs.Entity;
import com.github.krnl32.jupiter.ecs.Registry;
import com.github.krnl32.jupiter.ecs.System;
import com.github.krnl32.jupiter.renderer.RenderSpriteCommand;
import com.github.krnl32.jupiter.renderer.Renderer;
import com.github.krnl32.jupiter.renderer.SpriteRenderData;
import com.github.krnl32.jupiter.renderer.Texture2D;

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

			TextureAsset textureAsset = AssetManager.getInstance().getAsset(spriteRenderer.textureAssetID);
			if (textureAsset == null || !textureAsset.isLoaded())
				Logger.error("RenderSystem Failed to get Texture Asset({})\n", spriteRenderer.textureAssetID);

			Texture2D texture = (textureAsset != null && textureAsset.isLoaded()) ? textureAsset.getTexture() : null;
			renderer.submit(new RenderSpriteCommand(transform.translation, transform.rotation, new SpriteRenderData(transform.scale.x, transform.scale.y, spriteRenderer.index, spriteRenderer.color, texture)));
		}
	}
}
