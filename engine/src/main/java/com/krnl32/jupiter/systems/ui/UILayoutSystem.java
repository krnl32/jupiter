package com.krnl32.jupiter.systems.ui;

import com.krnl32.jupiter.components.ui.UIHierarchyComponent;
import com.krnl32.jupiter.components.ui.UILayoutComponent;
import com.krnl32.jupiter.components.ui.UIScrollComponent;
import com.krnl32.jupiter.components.ui.UITransformComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.ecs.Registry;
import com.krnl32.jupiter.ecs.System;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.ui.layout.LayoutOverflow;

public class UILayoutSystem implements System {
	private final Registry registry;

	public UILayoutSystem(Registry registry) {
		this.registry = registry;
	}

	@Override
	public void onUpdate(float dt) {
		for (Entity entity : registry.getEntitiesWith(UILayoutComponent.class, UIHierarchyComponent.class, UITransformComponent.class)) {
			UILayoutComponent layout = entity.getComponent(UILayoutComponent.class);
			UIHierarchyComponent hierarchy = entity.getComponent(UIHierarchyComponent.class);
			UITransformComponent transform = entity.getComponent(UITransformComponent.class);

			float containerWidth = transform.scale.x - (layout.paddingLeft + layout.paddingRight);
			float containerHeight  = transform.scale.y - (layout.paddingTop + layout.paddingBottom);

			switch (layout.type) {
				case HORIZONTAL -> layoutHorizontal(entity, layout, hierarchy, containerWidth, containerHeight);
				case VERTICAL -> layoutVertical(entity, layout, hierarchy, containerWidth, containerHeight);
				case ABSOLUTE -> {}
			}
		}
	}

	private void layoutHorizontal(Entity entity, UILayoutComponent layout, UIHierarchyComponent hierarchy, float containerWidth, float containerHeight) {
		// Calculate total height of children + spacing
		float totalWidth = 0.0f;
		int childCount = 0;

		for (Entity child : hierarchy.children) {
			UITransformComponent childTransform = child.getComponent(UITransformComponent.class);
			if (childTransform == null)
				continue;
			totalWidth += childTransform.scale.x;
			childCount++;
		}

		if (childCount > 1) {
			totalWidth += layout.spacing * (childCount - 1);
		}

		// if overflow scroll
		float scrollOffsetX = 0.0f;
		UIScrollComponent scroll = entity.getComponent(UIScrollComponent.class);
		if (layout.overflow == LayoutOverflow.SCROLL && scroll != null && scroll.scrollX) {
			float maxOffsetX = Math.max(0.0f, totalWidth - containerWidth);
			scroll.offset.x = Math.max(0.0f, Math.min(scroll.offset.x, maxOffsetX));
			scrollOffsetX = scroll.offset.x;
		}

		// if overflow is SCALE and totalWidth > containerWidth
		float scale = 1.0f;
		if (layout.overflow == LayoutOverflow.SCALE && totalWidth > containerWidth) {
			scale = containerWidth / totalWidth;
		}

		float cursorX = layout.paddingLeft - scrollOffsetX;
		for (Entity child : hierarchy.children) {
			UITransformComponent childTransform = child.getComponent(UITransformComponent.class);
			if (childTransform == null)
				continue;

			float childWidth = childTransform.scale.x * scale;
			float childHeight = layout.expandChildren ? containerHeight : childTransform.scale.y;
			childTransform.scale.set(childWidth, childHeight, childTransform.scale.z);

			// Handle Pivot & Position
			float x = cursorX - childTransform.pivot.x * childTransform.scale.x;
			float y = layout.paddingTop - childTransform.pivot.y * childTransform.scale.y;
			childTransform.translation.set(x, y, 0);
			cursorX += childTransform.scale.x + layout.spacing;
		}
	}

	private void layoutVertical(Entity entity, UILayoutComponent layout, UIHierarchyComponent hierarchy, float containerWidth, float containerHeight) {
		// Calculate total height of children + spacing
		float totalHeight = 0f;
		int childCount = 0;

		for (Entity child : hierarchy.children) {
			UITransformComponent childTransform = child.getComponent(UITransformComponent.class);
			if (childTransform == null) continue;
			totalHeight += childTransform.scale.y;
			childCount++;
		}
		if (childCount > 1) {
			totalHeight += layout.spacing * (childCount - 1);
		}

		// if overflow scroll
		UIScrollComponent scroll = entity.getComponent(UIScrollComponent.class);
		float scrollOffsetY = 0.0f;
		if (layout.overflow == LayoutOverflow.SCROLL && scroll != null && scroll.scrollY) {
			float maxOffsetY = Math.max(0f, totalHeight - containerHeight);
			scroll.offset.y = Math.max(0f, Math.min(scroll.offset.y, maxOffsetY));
			scrollOffsetY = scroll.offset.y;
		}

		// Handle Scale Overflow
		float scale = 1f;
		if (layout.overflow == LayoutOverflow.SCALE && totalHeight > containerHeight) {
			scale = containerHeight / totalHeight;
		}

		float cursorY = layout.paddingTop - scrollOffsetY;

		for (Entity child : hierarchy.children) {
			UITransformComponent childTransform = child.getComponent(UITransformComponent.class);
			if (childTransform == null)
				continue;

			float childHeight = childTransform.scale.y * scale;
			float childWidth = layout.expandChildren ? containerWidth : childTransform.scale.x;
			childTransform.scale.set(childWidth, childHeight, childTransform.scale.z);

			// Handle Pivot & Position
			float x = layout.paddingLeft - childTransform.pivot.x * childTransform.scale.x;
			float y = cursorY - childTransform.pivot.y * childTransform.scale.y;
			childTransform.translation.set(x, y, 0);
			cursorY += childTransform.scale.y + layout.spacing;
		}
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
