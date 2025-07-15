package com.krnl32.jupiter.panels;

import com.krnl32.jupiter.components.DestroyComponent;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.editor.EditorPanel;
import com.krnl32.jupiter.event.EventBus;
import com.krnl32.jupiter.events.entity.EntityDestroyedEvent;
import com.krnl32.jupiter.events.scene.EntitySelectedEvent;
import com.krnl32.jupiter.events.scene.SceneSwitchedEvent;
import com.krnl32.jupiter.scene.Scene;
import imgui.ImGui;
import imgui.flag.*;
import imgui.type.ImString;

import java.nio.ByteBuffer;

public class SceneHierarchyPanel implements EditorPanel {
	private Scene scene;
	private Entity selectedEntity;
	private final ImString renameBuffer;
	private boolean renaming;
	private Entity renameRequestedEntity;
	private boolean renameInputActiveLastFrame;

	public SceneHierarchyPanel(Scene scene) {
		this.scene = scene;
		this.selectedEntity = null;
		this.renameBuffer = new ImString(256);
		this.renaming = false;
		this.renameRequestedEntity = null;
		this.renameInputActiveLastFrame = false;

		EventBus.getInstance().register(EntityDestroyedEvent.class, event -> {
			setSelectedEntity(null);
		});

		EventBus.getInstance().register(SceneSwitchedEvent.class, event -> {
			this.scene = event.getScene();
			setSelectedEntity(null);
		});
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt) {
		ImGui.begin("SceneHierarchy");

		// Render Entities
		for (Entity entity : scene.getRegistry().getEntities()) {
			renderEntity(entity);
		}

		// Clear selection if clicking empty space
		if (ImGui.isWindowHovered() && ImGui.isMouseClicked(ImGuiMouseButton.Left) && !ImGui.isAnyItemHovered()) {
			setSelectedEntity(null);
			renaming = false;
		}

		// Open scene context menu on right click on empty space
		if (ImGui.isWindowHovered() && !ImGui.isAnyItemHovered() && ImGui.isMouseClicked(ImGuiMouseButton.Right)) {
			ImGui.openPopup("SceneHierarchyContextMenu");
		}

		// Check if any entity context menu is open
		boolean entityPopupOpen = false;
		for (Entity entity : scene.getRegistry().getEntities()) {
			if (ImGui.isPopupOpen("EntityContextMenu" + entity.getId())) {
				entityPopupOpen = true;
				break;
			}
		}

		// Scene context menu (right click on empty space)
		if (!entityPopupOpen && ImGui.beginPopupContextWindow("SceneHierarchyContextMenu")) {
			ImGui.separatorText("Scene Options");
			if (ImGui.menuItem("Create Entity")) {
				Entity newEntity = scene.createEntity();
				newEntity.setTag("New Entity");
				setSelectedEntity(newEntity);
				renaming = true;
				renameBuffer.set(newEntity.getTagOrId());
			}
			ImGui.endPopup();
		}

		ImGui.end();
	}

	private void renderEntity(Entity entity) {
		int nodeFlags = ImGuiTreeNodeFlags.SpanAvailWidth | ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.FramePadding;

		if (entity.equals(selectedEntity)) {
			nodeFlags |= ImGuiTreeNodeFlags.Selected;
		}

		String nodeId = "Entity_" + entity.getId();

		// Small bullet icon before the name
		ImGui.alignTextToFramePadding();
		ImGui.textColored(0.4f, 0.6f, 0.8f, 1f, "\u25CF");
		ImGui.sameLine();

		ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 6, 4);
		boolean nodeOpen = ImGui.treeNodeEx(nodeId, nodeFlags, entity.getTagOrId());
		ImGui.popStyleVar();

		// Click to select entity
		if (ImGui.isItemClicked()) {
			setSelectedEntity(entity);
			renaming = false;
		}

		// Right click context menu open
		if (ImGui.isItemClicked(ImGuiMouseButton.Right)) {
			ImGui.openPopup("EntityContextMenu" + entity.getId());
		}

		// Double click to rename
		if (ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(ImGuiMouseButton.Left)) {
			setSelectedEntity(entity);
			renaming = true;
			renameBuffer.set(entity.getTagOrId());
		}

		// Entity context menu
		if (ImGui.beginPopupContextItem("EntityContextMenu" + entity.getId(), 1)) {
			ImGui.separatorText("Entity Options");

			if (ImGui.menuItem("Rename")) {
				renameRequestedEntity = entity;
			}
			if (ImGui.menuItem("Delete")) {
				if (entity.equals(selectedEntity)) {
					setSelectedEntity(null);
				}
				entity.addComponent(new DestroyComponent());
				ImGui.endPopup();
				return;
			}
			ImGui.endPopup();
		}

		// Activate rename mode if requested (after popup closes)
		if (renameRequestedEntity != null) {
			renaming = true;
			setSelectedEntity(renameRequestedEntity);
			renameBuffer.set(selectedEntity.getTagOrId());
			renameRequestedEntity = null;
		}

		// Inline rename input field with robust focus handling
		if (renaming && entity.equals(selectedEntity)) {
			ImGui.setNextItemWidth(ImGui.getContentRegionAvailX());
			ImGui.pushStyleColor(ImGuiCol.FrameBg, 0.1f, 0.1f, 0.1f, 0.9f);
			ImGui.pushStyleColor(ImGuiCol.FrameBgHovered, 0.15f, 0.15f, 0.15f, 1.0f);
			ImGui.pushStyleColor(ImGuiCol.FrameBgActive, 0.2f, 0.2f, 0.2f, 1.0f);

			boolean renameConfirmed = ImGui.inputText("##rename", renameBuffer, ImGuiInputTextFlags.EnterReturnsTrue);

			ImGui.popStyleColor(3);

			if (renameConfirmed) {
				entity.setTag(renameBuffer.get());
				renaming = false;
			} else {
				if (!ImGui.isItemActive() && renameInputActiveLastFrame) {
					renaming = false;
				}
			}

			renameInputActiveLastFrame = ImGui.isItemActive();
		} else {
			renameInputActiveLastFrame = false;
		}

		// Drag & Drop support
		if (ImGui.beginDragDropSource()) {
			ByteBuffer payloadData = ByteBuffer.allocate(4);
			payloadData.putInt(entity.getId());
			payloadData.flip();

			ImGui.setDragDropPayload("ENTITY_PAYLOAD", payloadData);
			ImGui.text("Dragging: " + entity.getTagOrId());
			ImGui.endDragDropSource();
		}

		if (nodeOpen) {
			ImGui.treePop();
		}

		ImGui.spacing();
	}

	private void setSelectedEntity(Entity entity) {
		if ((selectedEntity == null && entity == null) || (selectedEntity != null && selectedEntity.equals(entity)))
			return;
		selectedEntity = entity;
		EventBus.getInstance().emit(new EntitySelectedEvent(selectedEntity));
	}
}
