package com.krnl32.jupiter.editor.panels;

import com.krnl32.jupiter.editor.editor.EditorPanel;
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
	private final Vector2f viewport;
	private boolean viewportFocused;

	public ViewportPanel(Framebuffer framebuffer) {
		this.framebuffer = framebuffer;
		this.viewport = new Vector2f();
		this.viewportFocused = false;

		EventBus.getInstance().register(SceneSwitchedEvent.class, event -> {
			EventBus.getInstance().emit(new ViewportResizeEvent((int) viewport.x, (int) viewport.y));
		});
	}

	@Override
	public void onUpdate(float dt) {
		if ((viewport.x > 0.0f && viewport.y > 0.0f) && (framebuffer.getWidth() != viewport.x || framebuffer.getHeight() != viewport.y)) {
			EventBus.getInstance().emit(new ViewportResizeEvent((int) viewport.x, (int) viewport.y));
		}
	}

	@Override
	public void onRender(float dt) {
		ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, new ImVec2(0, 0));
		ImGui.begin("Viewport");

		viewportFocused = ImGui.isWindowFocused();
		ImVec2 imViewport = ImGui.getContentRegionAvail();
		viewport.set(imViewport.x, imViewport.y);

		ImGui.image(framebuffer.getColorAttachmentID(0), new ImVec2(viewport.x, viewport.y), new ImVec2(0, 1), new ImVec2(1, 0));

		ImGui.end();
		ImGui.popStyleVar();
	}
}
