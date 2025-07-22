package com.krnl32.jupiter.systems.renderer;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.types.TextureAsset;
import com.krnl32.jupiter.components.effects.BlinkComponent;
import com.krnl32.jupiter.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.components.gameplay.TransformComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.RenderPacket;
import com.krnl32.jupiter.renderer.RenderSpriteCommand;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.renderer.Texture2D;

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

			if (entity.hasComponent(BlinkComponent.class)) {
				BlinkComponent blink = entity.getComponent(BlinkComponent.class);
				if (!blink.visible)
					continue;
			}

			TransformComponent transform = entity.getComponent(TransformComponent.class);
			SpriteRendererComponent spriteRenderer = entity.getComponent(SpriteRendererComponent.class);

			TextureAsset textureAsset = AssetManager.getInstance().getAsset(spriteRenderer.textureAssetID);
			if (spriteRenderer.textureAssetID != null && (textureAsset == null || !textureAsset.isLoaded()))
				Logger.error("RenderSystem Failed to get Texture Asset({})\n", spriteRenderer.textureAssetID);

			Texture2D texture = (textureAsset != null && textureAsset.isLoaded()) ? textureAsset.getTexture() : null;
			renderer.submit(new RenderSpriteCommand(new RenderPacket(spriteRenderer.index, spriteRenderer.color, texture), transform.getTransform(), spriteRenderer.textureUV));
		}
	}
}
