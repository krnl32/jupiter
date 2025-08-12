package com.krnl32.jupiter.engine.systems.renderer;

import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.ecs.Registry;
import com.krnl32.jupiter.engine.ecs.System;
import com.krnl32.jupiter.engine.project.ProjectContext;
import com.krnl32.jupiter.engine.renderer.RenderPacket;
import com.krnl32.jupiter.engine.renderer.rendercommand.types.RenderSpriteCommand;
import com.krnl32.jupiter.engine.renderer.Renderer;
import com.krnl32.jupiter.engine.renderer.texture.Texture2D;

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

			Texture2D texture = null;
			if (spriteRenderer.textureAssetId != null) {
				TextureAsset textureAsset = ProjectContext.getInstance().getAssetManager().getAsset(spriteRenderer.textureAssetId);
				if (spriteRenderer.textureAssetId != null && (textureAsset == null || !textureAsset.isValid())) {
					Logger.error("RenderSystem Failed to get Texture Asset({})\n", spriteRenderer.textureAssetId);
				}

				texture = (textureAsset != null && textureAsset.isValid()) ? textureAsset.getTexture() : null;
			}

			renderer.submit(new RenderSpriteCommand(new RenderPacket(spriteRenderer.index, spriteRenderer.color, texture), transform.getTransform(), spriteRenderer.textureUV));
		}
	}
}
