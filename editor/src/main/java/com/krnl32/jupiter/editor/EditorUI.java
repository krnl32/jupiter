package com.krnl32.jupiter.editor;

import com.krnl32.jupiter.core.Window;
import imgui.ImGui;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

import java.util.ArrayList;
import java.util.List;

public class EditorUI {
	private final ImGuiWrapper imGuiWrapper;
	private final List<EditorWindow> editorWindows = new ArrayList<>();

	public EditorUI(Window window) {
		this.imGuiWrapper = new ImGuiWrapper(window);
	}

	public void onRender(float dt) {
		imGuiWrapper.beginFrame();

		renderDockspace();
		renderViewport();

		for (EditorWindow editorWindow : editorWindows) {
			editorWindow.onRender(dt);
		}

		imGuiWrapper.endFrame();
	}

	public void destroy() {
		imGuiWrapper.destroy();
	}

	public void addEditorWindow(EditorWindow editorWindow) {
		editorWindows.add(editorWindow);
	}

	private void renderDockspace() {
		var viewport = ImGui.getMainViewport();

		int windowFlags = ImGuiWindowFlags.NoTitleBar
			| ImGuiWindowFlags.NoCollapse
			| ImGuiWindowFlags.NoResize
			| ImGuiWindowFlags.NoMove
			| ImGuiWindowFlags.NoBringToFrontOnFocus
			| ImGuiWindowFlags.NoNavFocus;

		ImGui.setNextWindowPos(viewport.getWorkPosX(), viewport.getWorkPosY());
		ImGui.setNextWindowSize(viewport.getWorkSizeX(), viewport.getWorkSizeY());
		ImGui.setNextWindowViewport(viewport.getID());

		ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
		ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);

		ImBoolean showDockspace = new ImBoolean(true);
		ImGui.begin("Editor", showDockspace, windowFlags);

		ImGui.popStyleVar(2);

		int dockspaceFlags = ImGuiDockNodeFlags.PassthruCentralNode;
		int dockspaceId = ImGui.getID("MyDockSpace");
		ImGui.dockSpace(dockspaceId, 0, 0, dockspaceFlags);
		ImGui.end();
	}

	private void renderViewport() {
		ImGui.begin("Viewport");

		ImGui.end();
	}
}
