package com.krnl32.jupiter.editor;

import com.krnl32.jupiter.core.Window;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

import java.util.ArrayList;
import java.util.List;

public class EditorUI {
	private final ImGuiWrapper imGuiWrapper;
	private final List<EditorPanel> editorPanels;

	public EditorUI(Window window) {
		this.imGuiWrapper = new ImGuiWrapper(window);
		this.editorPanels = new ArrayList<>();
	}

	public void onUpdate(float dt) {
		for (EditorPanel editorPanel : editorPanels) {
			editorPanel.onUpdate(dt);
		}
	}

	public void onRender(float dt) {
		imGuiWrapper.beginFrame();
		dockspaceBegin();

		for (EditorPanel editorPanel : editorPanels) {
			editorPanel.onRender(dt);
		}

		dockspaceEnd();
		imGuiWrapper.endFrame();
	}

	public void destroy() {
		imGuiWrapper.destroy();
	}

	public void addEditorPanel(EditorPanel editorPanel) {
		editorPanels.add(editorPanel);
	}

	private void dockspaceBegin() {
		boolean dockspaceOpen = true;
		boolean optFullscreen = true;
		boolean optPadding = false;
		int dockspaceFlags = ImGuiDockNodeFlags.None;

		int windowFlags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;
		if (optFullscreen) {
			var viewport = ImGui.getMainViewport();
			ImGui.setNextWindowPos(viewport.getWorkPos());
			ImGui.setNextWindowSize(viewport.getWorkSize());
			ImGui.setNextWindowViewport(viewport.getID());
			ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
			ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);
			windowFlags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove;
			windowFlags |= ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;
		} else {
			dockspaceFlags &= ~ImGuiDockNodeFlags.PassthruCentralNode;
		}

		if ((dockspaceFlags & ImGuiDockNodeFlags.PassthruCentralNode) != 0) {
			windowFlags |= ImGuiWindowFlags.NoBackground;
		}

		if (!optPadding) {
			ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, new ImVec2(0.0f, 0.0f));
		}

		ImGui.begin("Dockspace", new ImBoolean(dockspaceOpen), windowFlags);

		if (!optPadding)
			ImGui.popStyleVar();

		if (optFullscreen)
			ImGui.popStyleVar(2);

		if ((ImGui.getIO().getConfigFlags() & ImGuiConfigFlags.DockingEnable) != 0)
		{
			int dockspaceId = ImGui.getID("MyDockSpace");
			ImGui.dockSpace(dockspaceId, new ImVec2(0.0f, 0.0f), dockspaceFlags);
		}
	}

	private void dockspaceEnd() {
		ImGui.end();
	}
}
