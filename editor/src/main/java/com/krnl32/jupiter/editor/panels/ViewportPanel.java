package com.krnl32.jupiter.editor.panels;

import com.krnl32.jupiter.editor.editor.EditorCamera;
import com.krnl32.jupiter.editor.ui.EditorPanel;
import com.krnl32.jupiter.editor.tools.gizmo.GizmoController;
import com.krnl32.jupiter.editor.events.scene.EntitySelectedEvent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.scene.SceneSwitchedEvent;
import com.krnl32.jupiter.engine.events.scene.ViewportResizeEvent;
import com.krnl32.jupiter.engine.renderer.Framebuffer;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiStyleVar;
import org.joml.Vector2f;

public class ViewportPanel implements EditorPanel {
	private final Framebuffer framebuffer;
	private final EditorCamera editorCamera;
	private final Vector2f viewport;
	private boolean viewportFocused;

	private final GizmoController gizmoController;
	private Entity selectedEntity;

	public ViewportPanel(Framebuffer framebuffer, EditorCamera editorCamera) {
		this.framebuffer = framebuffer;
		this.editorCamera = editorCamera;
		this.viewport = new Vector2f();
		this.viewportFocused = false;

		this.gizmoController = new GizmoController(editorCamera);

		EventBus.getInstance().register(SceneSwitchedEvent.class, event -> {
			EventBus.getInstance().emit(new ViewportResizeEvent((int) viewport.x, (int) viewport.y));
		});

		EventBus.getInstance().register(EntitySelectedEvent.class, event -> {
			selectedEntity = event.getEntity();
		});
	}

	@Override
	public void onUpdate(float dt) {
		if ((viewport.x > 0.0f && viewport.y > 0.0f) && (framebuffer.getWidth() != viewport.x || framebuffer.getHeight() != viewport.y)) {
			EventBus.getInstance().emit(new ViewportResizeEvent((int) viewport.x, (int) viewport.y));
		}

		gizmoController.onUpdate(dt);
	}

	@Override
	public void onRender(float dt) {
		ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, new ImVec2(0, 0));
		ImGui.begin("Viewport");

		viewportFocused = ImGui.isWindowFocused();
		ImVec2 imViewport = ImGui.getContentRegionAvail();
		viewport.set(imViewport.x, imViewport.y);

		ImGui.image(framebuffer.getColorAttachmentID(0), new ImVec2(viewport.x, viewport.y), new ImVec2(0, 1), new ImVec2(1, 0));

		// ImGuizmo
		if (selectedEntity != null) {
			// Viewport Rect
			ImVec2 windowPos = ImGui.getWindowPos();
			ImVec2 contentRegionMin = ImGui.getWindowContentRegionMin();
			ImVec2 contentRegionMax = ImGui.getWindowContentRegionMax();

			// Top-Left of Content Region
			float gizmoX = windowPos.x + contentRegionMin.x;
			float gizmoY = windowPos.y + contentRegionMin.y;

			// Width/Height of Content Region
			float gizmoWidth = contentRegionMax.x - contentRegionMin.x;
			float gizmoHeight = contentRegionMax.y - contentRegionMin.y;

			gizmoController.onRender(dt, selectedEntity, new Vector2f(gizmoX, gizmoY), new Vector2f(gizmoWidth, gizmoHeight));
		}

		ImGui.end();
		ImGui.popStyleVar();
	}
}
