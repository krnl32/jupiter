package com.krnl32.jupiter.systems.ui;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.TextureAsset;
import com.krnl32.jupiter.components.ui.UIRenderComponent;
import com.krnl32.jupiter.components.ui.UITransformComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.RenderPacket;
import com.krnl32.jupiter.renderer.RenderUICommand;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.renderer.Texture2D;
import org.joml.Matrix4f;

public class UIRenderSystem implements System {
	public final Registry registry;

	public UIRenderSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		for (Entity entity : registry.getEntitiesWith(UITransformComponent.class, UIRenderComponent.class)) {
			UITransformComponent transformComponent = entity.getComponent(UITransformComponent.class);
			UIRenderComponent renderComponent = entity.getComponent(UIRenderComponent.class);

			Matrix4f transformMatrix = new Matrix4f().translate(transformComponent.translation).rotateXYZ(transformComponent.rotation).scale(transformComponent.scale);

			Texture2D texture = null;
			if (renderComponent.textureAssetID != null) {
				TextureAsset textureAsset = AssetManager.getInstance().getAsset(renderComponent.textureAssetID);
				if (textureAsset == null || !textureAsset.isLoaded())
					Logger.error("UIRenderSystem Failed to get Texture Asset({})\n", renderComponent.textureAssetID);
				texture = (textureAsset != null && textureAsset.isLoaded()) ? textureAsset.getTexture() : null;
			}
			renderer.submit(new RenderUICommand(new RenderPacket(renderComponent.index, renderComponent.color, texture), transformMatrix, renderComponent.textureUV));
		}
	}
}
