package com.krnl32.jupiter.systems.ui;

import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.types.TextureAsset;
import com.krnl32.jupiter.components.ui.UIClipComponent;
import com.krnl32.jupiter.components.ui.UIHierarchyComponent;
import com.krnl32.jupiter.components.ui.UIRenderComponent;
import com.krnl32.jupiter.components.ui.UITransformComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.*;
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
		for (Entity entity : registry.getEntitiesWith(UITransformComponent.class)) {
			UIHierarchyComponent uiHierarchy = entity.getComponent(UIHierarchyComponent.class);
			if (uiHierarchy == null || uiHierarchy.parent == null) {
				renderHierarchy(renderer, entity, new Matrix4f(), null);
			}
		}
	}

	private void renderHierarchy(Renderer renderer, Entity entity, Matrix4f parentTransform, ClipRect parentClipRect) {
		UITransformComponent transformComponent = entity.getComponent(UITransformComponent.class);
		UIRenderComponent renderComponent = entity.getComponent(UIRenderComponent.class);

		Matrix4f worldTransform;
		if(renderComponent != null) {
			// Remove scale from parent transform
			Vector3f parentScale = new Vector3f();
			parentTransform.getScale(parentScale);
			Matrix4f parentTransformNoScale = new Matrix4f(parentTransform).scale(1.0f / parentScale.x, 1.0f / parentScale.y, 1.0f / parentScale.z);
			Matrix4f localTransform = new Matrix4f().translate(transformComponent.translation).rotateXYZ(transformComponent.rotation).scale(transformComponent.scale);
			worldTransform = new Matrix4f(parentTransformNoScale).mul(localTransform);
		} else {
			Matrix4f localTransform = new Matrix4f().translate(transformComponent.translation).rotateXYZ(transformComponent.rotation).scale(transformComponent.scale);
			worldTransform = new Matrix4f(parentTransform).mul(localTransform);
		}

		// Handle Clipping
		ClipRect localClipRect = parentClipRect;
		UIClipComponent clip = entity.getComponent(UIClipComponent.class);
		if (clip != null) {
			localClipRect = new ClipRect(clip.x, clip.y, clip.width, clip.height);
			if (parentClipRect != null) {
				localClipRect = localClipRect.intersect(parentClipRect);
			}
		}

		if (renderComponent != null) {
			Texture2D texture = null;
			if (renderComponent.textureAssetID != null) {
				TextureAsset textureAsset = AssetManager.getInstance().getAsset(renderComponent.textureAssetID);
				if (textureAsset == null || !textureAsset.isLoaded())
					Logger.error("UIRenderSystem Failed to get Texture Asset({})\n", renderComponent.textureAssetID);
				texture = (textureAsset != null && textureAsset.isLoaded()) ? textureAsset.getTexture() : null;
			}

			renderer.submit(new RenderUICommand(new RenderPacket(renderComponent.index, renderComponent.color, texture), worldTransform, renderComponent.textureUV, localClipRect));
		}

		UIHierarchyComponent childHierarchy = entity.getComponent(UIHierarchyComponent.class);
		if (childHierarchy != null) {
			for (Entity child : childHierarchy.children) {
				if (child.hasComponent(UITransformComponent.class) && child.hasComponent(UIRenderComponent.class)) {
					renderHierarchy(renderer, child, worldTransform, localClipRect);
				}
			}
		}
	}
}
