package com.krnl32.jupiter.editor.panels;

import com.krnl32.jupiter.editor.editor.EditorPanel;
import com.krnl32.jupiter.editor.factory.ComponentFactory;
import com.krnl32.jupiter.editor.factory.FactoryRegistry;
import com.krnl32.jupiter.editor.renderer.component.ComponentRenderer;
import com.krnl32.jupiter.editor.renderer.EditorRendererRegistry;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ecs.Entity;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImString;

import java.util.ArrayList;
import java.util.List;

public class EntityInspectorPanel implements EditorPanel {
	private Entity selectedEntity;
	private final ImString addComponentFilter;
	private final List<Float> hoverAnimProgresses;
	private Component componentToRemove = null;

	// Styling
	private static final float[] BASE_COLOR = {0.6f, 0.6f, 0.6f, 1f};
	private static final float[] HOVER_COLOR = {0.9f, 0.7f, 1f, 1f};

	public EntityInspectorPanel() {
		selectedEntity = null;
		addComponentFilter = new ImString(256);
		hoverAnimProgresses = new ArrayList<>();
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt) {
		if (selectedEntity != null) {
			renderComponents(selectedEntity, dt);
		}
	}

	public void setSelectedEntity(Entity selectedEntity) {
		this.selectedEntity = selectedEntity;
		hoverAnimProgresses.clear();
	}

	private void renderComponents(Entity entity, float dt) {
		List<Component> components = new ArrayList<>(entity.getComponents());
		syncHoverProgress(components.size());

		for (int i = 0; i < components.size(); i++) {
			Component component = components.get(i);
			Class<? extends Component> componentClass = component.getClass();
			@SuppressWarnings("unchecked")
			ComponentRenderer<Component> componentRenderer = (ComponentRenderer<Component>) EditorRendererRegistry.getComponentRenderer(componentClass);
			if (componentRenderer == null) {
				continue;
			}

			// Animate Hover Color
			hoverAnimProgresses.set(i, updateHoverProgress(hoverAnimProgresses.get(i), dt));
			float t = hoverAnimProgresses.get(i);
			float[] interpolatedColor = lerpColor(BASE_COLOR, HOVER_COLOR, t);
			ImGui.pushStyleColor(ImGuiCol.Header, interpolatedColor[0], interpolatedColor[1], interpolatedColor[2], interpolatedColor[3]);

			// Render X(Remove) button
			ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.1f, 0.1f, 1f);
			ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1f, 0.3f, 0.3f, 1f);
			ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1f, 0.2f, 0.2f, 1f);
			if (ImGui.button("X##" + componentClass.getSimpleName(), 22, 22)) {
				componentToRemove = component;  // mark for deferred removal
			}
			ImGui.popStyleColor(3);

			ImGui.sameLine();

			String headerLabel = getComponentIcon(componentClass) + " " + componentClass.getSimpleName();
			boolean open = ImGui.collapsingHeader(headerLabel, ImGuiTreeNodeFlags.DefaultOpen);
			ImGui.popStyleColor();

			if (open) {
				// Render Component
				ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 8, 6);
				ImGui.separator();
				componentRenderer.render(component);
				ImGui.separator();
				ImGui.popStyleVar();
			}
		}

		if (componentToRemove != null) {
			entity.removeComponent(componentToRemove.getClass());
			componentToRemove = null;
			hoverAnimProgresses.clear();
		}

		renderAddComponentButton(entity);
	}

	private void syncHoverProgress(int size) {
		while (hoverAnimProgresses.size() < size)
			hoverAnimProgresses.add(0.0f);
		while (hoverAnimProgresses.size() > size)
			hoverAnimProgresses.remove(hoverAnimProgresses.size() - 1);
	}

	private void renderAddComponentButton(Entity entity) {
		ImGui.spacing();

		ImGui.pushStyleColor(ImGuiCol.Button, 0.2f, 0.6f, 0.9f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.3f, 0.7f, 1f, 1f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.1f, 0.5f, 0.8f, 1f);

		if (ImGui.button("Add Component")) {
			ImGui.openPopup("AddComponentPopup");
		}
		if (ImGui.isItemHovered()) {
			ImGui.setTooltip("Add a new component to the entity");
		}

		ImGui.popStyleColor(3);

		renderAddComponentPopup(entity);
	}

	private void renderAddComponentPopup(Entity entity) {
		if (!ImGui.beginPopup("AddComponentPopup"))
			return;

		ImGui.text("Select Component to Add:");
		ImGui.separator();
		ImGui.spacing();

		ImGui.pushItemWidth(-1);
		ImGui.inputText("##filter", addComponentFilter);
		ImGui.popItemWidth();

		ImGui.spacing();

		EditorRendererRegistry.getComponentRenderers().keySet().stream()
			.filter(type -> !entity.hasComponent(type))
			.filter(type -> {
				String name = type.getSimpleName().toLowerCase();
				return addComponentFilter.get().isEmpty() || name.contains(addComponentFilter.get().toLowerCase());
			})
			.forEach(type -> {
				if (ImGui.menuItem(type.getSimpleName())) {
					ComponentFactory<? extends Component> factory = FactoryRegistry.getComponentFactory(type);
					if (factory != null) {
						Component newComponent = factory.create();
						if (newComponent != null) {
							entity.addComponent(newComponent);
						}
					} else {
						Logger.error("InspectorPanel: No Component Factory Found For({})", type.getSimpleName());
					}
					ImGui.closeCurrentPopup();
					addComponentFilter.set("");
				}
			});

		ImGui.endPopup();
	}

	private float updateHoverProgress(float current, float dt) {
		boolean hovered = ImGui.isItemHovered();
		float speed = 5.0f;
		if (hovered) {
			current = Math.min(1f, current + dt * speed);
		} else {
			current = Math.max(0f, current - dt * speed);
		}
		return current;
	}

	private float[] lerpColor(float[] start, float[] end, float t) {
		float[] result = new float[start.length];
		for (int i = 0; i < start.length; i++) {
			result[i] = start[i] + t * (end[i] - start[i]);
		}
		return result;
	}

	private String getComponentIcon(Class<? extends Component> componentClass) {
		String name = componentClass.getSimpleName().toLowerCase();
		if (name.contains("transformcomponent"))
			return "\uf0b2";
		return "📦";
	}
}
