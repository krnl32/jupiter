package com.krnl32.jupiter.launcher.launcher;

import com.krnl32.jupiter.engine.core.Window;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.window.WindowCloseEvent;
import com.krnl32.jupiter.launcher.events.ui.OpenSettingsPanelEvent;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.ImVec4;
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

		renderMenuBar();

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
		var style = ImGui.getStyle();
		var colors = style.getColors();

		style.setFrameRounding(4.0f);
		style.setWindowRounding(4.0f);
		style.setGrabRounding(2.0f);
		style.setScrollbarRounding(2.0f);
		style.setScrollbarRounding(4.0f);
		style.setFrameBorderSize(0.0f);
		style.setWindowBorderSize(0.0f);
		style.setPopupBorderSize(0.0f);
		style.setItemSpacing(8, 6);
		style.setWindowPadding(10, 10);

		colors[ImGuiCol.WindowBg] = new ImVec4(0.11f, 0.11f, 0.14f, 1.0f);
		colors[ImGuiCol.Header] = new ImVec4(0.20f, 0.22f, 0.27f, 1.0f);
		colors[ImGuiCol.HeaderHovered] = new ImVec4(0.32f, 0.34f, 0.39f, 1.0f);
		colors[ImGuiCol.HeaderActive] = new ImVec4(0.25f, 0.28f, 0.33f, 1.0f);

		colors[ImGuiCol.Button] = new ImVec4(0.20f, 0.22f, 0.27f, 1.0f);
		colors[ImGuiCol.ButtonHovered] = new ImVec4(0.32f, 0.34f, 0.39f, 1.0f);
		colors[ImGuiCol.ButtonActive] = new ImVec4(0.25f, 0.28f, 0.33f, 1.0f);

		colors[ImGuiCol.FrameBg] = new ImVec4(0.16f, 0.18f, 0.22f, 1.0f);
		colors[ImGuiCol.FrameBgHovered] = new ImVec4(0.26f, 0.28f, 0.32f, 1.0f);
		colors[ImGuiCol.FrameBgActive] = new ImVec4(0.20f, 0.22f, 0.27f, 1.0f);

		colors[ImGuiCol.TitleBg] = new ImVec4(0.09f, 0.09f, 0.11f, 1.0f);
		colors[ImGuiCol.TitleBgActive] = new ImVec4(0.12f, 0.12f, 0.15f, 1.0f);

		colors[ImGuiCol.Tab] = new ImVec4(0.15f, 0.16f, 0.20f, 1.0f);
		colors[ImGuiCol.TabHovered] = new ImVec4(0.28f, 0.29f, 0.35f, 1.0f);
		colors[ImGuiCol.TabActive] = new ImVec4(0.20f, 0.22f, 0.27f, 1.0f);
		colors[ImGuiCol.TabUnfocused] = new ImVec4(0.12f, 0.13f, 0.16f, 1.0f);
		colors[ImGuiCol.TabUnfocusedActive] = new ImVec4(0.17f, 0.18f, 0.22f, 1.0f);

		colors[ImGuiCol.CheckMark] = new ImVec4(0.89f, 0.64f, 0.14f, 1.0f);
		colors[ImGuiCol.SliderGrab] = new ImVec4(0.69f, 0.52f, 0.09f, 1.0f);
		colors[ImGuiCol.SliderGrabActive] = new ImVec4(0.89f, 0.64f, 0.14f, 1.0f);

		colors[ImGuiCol.Separator] = new ImVec4(0.19f, 0.19f, 0.22f, 1.0f);
		colors[ImGuiCol.SeparatorHovered] = new ImVec4(0.35f, 0.35f, 0.40f, 1.0f);
		colors[ImGuiCol.SeparatorActive] = new ImVec4(0.40f, 0.40f, 0.47f, 1.0f);
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

	private void renderMenuBar() {
		if (ImGui.beginMenuBar()) {
			if (ImGui.beginMenu("File")) {
				if (ImGui.menuItem("Exit")) {
					EventBus.getInstance().emit(new WindowCloseEvent());
				}

				if (ImGui.menuItem("Settings")) {
					EventBus.getInstance().emit(new OpenSettingsPanelEvent());
				}
				ImGui.endMenu();
			}
			ImGui.endMenuBar();
		}
	}
}
