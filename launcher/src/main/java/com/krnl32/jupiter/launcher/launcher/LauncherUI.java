package com.krnl32.jupiter.launcher.launcher;

import com.krnl32.jupiter.engine.core.Window;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.*;
import imgui.type.ImBoolean;

import java.util.ArrayList;
import java.util.List;

public class LauncherUI {
	private final ImGuiWrapper imGuiWrapper;
	private final List<UIPanel> uiPanels;

	public LauncherUI(Window window) {
		this.imGuiWrapper = new ImGuiWrapper(window);
		this.uiPanels = new ArrayList<>();
		setupTheme();
	}

	public void onUpdate(float dt) {
		for (var uiPanel : uiPanels) {
			uiPanel.onUpdate(dt);
		}
	}

	public void onRender(float dt) {
		imGuiWrapper.beginFrame();
		dockspaceBegin();

		for (var uiPanel : uiPanels) {
			uiPanel.onRender(dt);
		}

		dockspaceEnd();
		imGuiWrapper.endFrame();
	}

	public void destroy() {
		imGuiWrapper.destroy();
	}

	public void addUIPanel(UIPanel uiPanel) {
		this.uiPanels.add(uiPanel);
	}

	private void setupTheme() {
		ImGui.getStyle().setWindowRounding(6);
		ImGui.getStyle().setFrameRounding(4);
		ImGui.getStyle().setGrabRounding(3);
		ImGui.getStyle().setWindowBorderSize(1.0f);

		ImGui.getStyle().setColor(ImGuiCol.WindowBg, 0.1f, 0.1f, 0.1f, 1.0f);
		ImGui.getStyle().setColor(ImGuiCol.Header, 0.25f, 0.25f, 0.25f, 1.0f);
		ImGui.getStyle().setColor(ImGuiCol.Button, 0.2f, 0.4f, 0.6f, 1.0f);
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
	}}
