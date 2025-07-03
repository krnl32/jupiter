package com.krnl32.jupiter.systems.ui;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.TextureAsset;
import com.krnl32.jupiter.components.ui.UIHierarchyComponent;
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
import org.joml.Vector3f;

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
			UIHierarchyComponent uiHierarchy = entity.getComponent(UIHierarchyComponent.class);
			if (uiHierarchy == null || uiHierarchy.parent == null) {
				renderHierarchy(renderer, entity, new Matrix4f());
			}
		}
	}

	private void renderHierarchy(Renderer renderer, Entity entity, Matrix4f parentTransform) {
		UITransformComponent transformComponent = entity.getComponent(UITransformComponent.class);
		UIRenderComponent renderComponent = entity.getComponent(UIRenderComponent.class);

		// Remove scale from parent transform
		Vector3f parentScale = new Vector3f();
		parentTransform.getScale(parentScale);
		Matrix4f parentTransformNoScale = new Matrix4f(parentTransform).scale(1.0f / parentScale.x, 1.0f / parentScale.y, 1.0f / parentScale.z);

		Matrix4f localTransform = new Matrix4f().translate(transformComponent.translation).rotateXYZ(transformComponent.rotation).scale(transformComponent.scale);
		Matrix4f worldTransform = new Matrix4f(parentTransformNoScale).mul(localTransform);

		Texture2D texture = null;
		if (renderComponent.textureAssetID != null) {
			TextureAsset textureAsset = AssetManager.getInstance().getAsset(renderComponent.textureAssetID);
			if (textureAsset == null || !textureAsset.isLoaded())
				Logger.error("UIRenderSystem Failed to get Texture Asset({})\n", renderComponent.textureAssetID);
			texture = (textureAsset != null && textureAsset.isLoaded()) ? textureAsset.getTexture() : null;
		}

		renderer.submit(new RenderUICommand(new RenderPacket(renderComponent.index, renderComponent.color, texture), worldTransform, renderComponent.textureUV));

		UIHierarchyComponent childHierarchy = entity.getComponent(UIHierarchyComponent.class);
		if (childHierarchy != null) {
			for (Entity child : childHierarchy.children) {
				if (child.hasComponent(UITransformComponent.class) && child.hasComponent(UIRenderComponent.class)) {
					renderHierarchy(renderer, child, worldTransform);
				}
			}
		}
	}
}
