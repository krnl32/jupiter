package com.krnl32.jupiter.editor.ui;

import com.krnl32.jupiter.editor.editor.EditorState;
import com.krnl32.jupiter.editor.editor.ImGuiWrapper;
import com.krnl32.jupiter.editor.events.editor.*;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.core.Window;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.renderer.texture.*;
import com.krnl32.jupiter.engine.utility.FileIO;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.ImVec2;
import imgui.flag.*;
import imgui.type.ImBoolean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditorUI {
	private final ImGuiWrapper imGuiWrapper;
	private final List<EditorPanel> editorPanels;
	private EditorState editorState;

	// Editor Icons
	int playIconTextureId;
	int pauseIconTextureId;
	int stopIconTextureId;
	int buildIconTextureId;
	int launchIconTextureId;

	public EditorUI(Window window) {
		this.imGuiWrapper = new ImGuiWrapper(window);
		this.editorPanels = new ArrayList<>();
		this.editorState = EditorState.STOP;

		setupDarkTheme();

		if (!setupEditorIcons()) {
			return;
		}

		EventBus.getInstance().register(EditorPlayEvent.class, event -> {
			editorState = EditorState.PLAY;
		});
		EventBus.getInstance().register(EditorPauseEvent.class, event -> {
			editorState = EditorState.PAUSE;
		});
		EventBus.getInstance().register(EditorStopEvent.class, event -> {
			editorState = EditorState.STOP;
		});
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

	public static void setupDarkTheme() {
		// Clear Color: 0.06f, 0.07f, 0.09f, 1.0f
		ImGuiStyle style = ImGui.getStyle();

		// Color Palette
		style.setColor(ImGuiCol.WindowBg,0.07f, 0.08f, 0.10f, 1.0f);
		style.setColor(ImGuiCol.ChildBg,0.09f, 0.10f, 0.12f, 1.0f);

		style.setColor(ImGuiCol.Text, 0.89f, 0.89f, 0.92f, 1.0f);
		style.setColor(ImGuiCol.TextDisabled, 0.44f, 0.44f, 0.48f, 1.0f);

		// Headers
		style.setColor(ImGuiCol.Header, 0.18f, 0.22f, 0.30f, 1.0f);
		style.setColor(ImGuiCol.HeaderHovered, 0.27f, 0.32f, 0.40f, 1.0f);
		style.setColor(ImGuiCol.HeaderActive,0.23f, 0.37f, 0.50f, 1.0f);

		// Buttons
		style.setColor(ImGuiCol.Button, 0.15f, 0.18f, 0.22f, 1.0f);
		style.setColor(ImGuiCol.ButtonHovered, 0.25f, 0.29f, 0.35f, 1.0f);
		style.setColor(ImGuiCol.ButtonActive, 0.20f, 0.25f, 0.33f, 1.0f);

		// Frames
		style.setColor(ImGuiCol.FrameBg, 0.13f, 0.14f, 0.17f, 1.0f);
		style.setColor(ImGuiCol.FrameBgHovered, 0.22f, 0.24f, 0.29f, 1.0f);
		style.setColor(ImGuiCol.FrameBgActive, 0.25f, 0.28f, 0.34f, 1.0f);

		// Tabs
		style.setColor(ImGuiCol.Tab, 0.14f, 0.16f, 0.22f, 1.0f);
		style.setColor(ImGuiCol.TabHovered, 0.26f, 0.30f, 0.38f, 1.0f);
		style.setColor(ImGuiCol.TabActive, 0.20f, 0.24f, 0.32f, 1.0f);
		style.setColor(ImGuiCol.TabUnfocused, 0.10f, 0.12f, 0.16f, 1.0f);
		style.setColor(ImGuiCol.TabUnfocusedActive, 0.16f, 0.20f, 0.26f, 1.0f);

		// Sliders, Grabs
		style.setColor(ImGuiCol.SliderGrab, 0.40f, 0.60f, 0.95f, 1.0f);
		style.setColor(ImGuiCol.SliderGrabActive, 0.50f, 0.70f, 1.00f, 1.0f);

		// Checkmark, Radio, Resize
		style.setColor(ImGuiCol.CheckMark, 0.45f, 0.70f, 1.00f, 1.0f);
		style.setColor(ImGuiCol.ResizeGrip, 0.20f, 0.23f, 0.28f, 1.0f);
		style.setColor(ImGuiCol.ResizeGripHovered, 0.35f, 0.45f, 0.55f, 1.0f);
		style.setColor(ImGuiCol.ResizeGripActive, 0.50f, 0.60f, 0.70f, 1.0f);

		// Scrollbars
		style.setColor(ImGuiCol.ScrollbarBg, 0.09f, 0.10f, 0.12f, 1.0f);
		style.setColor(ImGuiCol.ScrollbarGrab, 0.18f, 0.20f, 0.25f, 1.0f);
		style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.28f, 0.30f, 0.35f, 1.0f);
		style.setColor(ImGuiCol.ScrollbarGrabActive, 0.34f, 0.36f, 0.42f, 1.0f);

		// Separators
		style.setColor(ImGuiCol.Separator, 0.20f, 0.22f, 0.26f, 1.0f);
		style.setColor(ImGuiCol.SeparatorHovered, 0.40f, 0.50f, 0.60f, 1.0f);
		style.setColor(ImGuiCol.SeparatorActive, 0.50f, 0.60f, 0.70f, 1.0f);

		// Popup
		style.setColor(ImGuiCol.PopupBg, 0.10f, 0.11f, 0.14f, 1.0f);
		style.setColor(ImGuiCol.MenuBarBg, 0.12f, 0.13f, 0.16f, 1.0f);

		// Style Vars
		style.setWindowRounding(6.0f);
		style.setChildRounding(6.0f);
		style.setPopupRounding(6.0f);
		style.setFrameRounding(4.0f);
		style.setScrollbarRounding(4.0f);
		style.setGrabRounding(4.0f);

		style.setItemSpacing(10f, 6f);
		style.setItemInnerSpacing(8f, 4f);
		style.setFramePadding(6f, 4f);

		style.setWindowBorderSize(1.0f);
		style.setChildBorderSize(1.0f);
		style.setPopupBorderSize(1.0f);
	}

	private boolean setupEditorIcons() {
		// Icons
		try {
			playIconTextureId = new Texture2D(new TextureSettings(TextureType.TEXTURE_2D, TextureWrapMode.REPEAT, TextureFilterMode.NEAREST, true), FileIO.readResourceFileContentBytes("textures/ui/buttons/play.png")).getTextureID();
			pauseIconTextureId = new Texture2D(new TextureSettings(TextureType.TEXTURE_2D, TextureWrapMode.REPEAT, TextureFilterMode.NEAREST, true), FileIO.readResourceFileContentBytes("textures/ui/buttons/pause.png")).getTextureID();
			stopIconTextureId = new Texture2D(new TextureSettings(TextureType.TEXTURE_2D, TextureWrapMode.REPEAT, TextureFilterMode.NEAREST, true), FileIO.readResourceFileContentBytes("textures/ui/buttons/stop.png")).getTextureID();
			buildIconTextureId = new Texture2D(new TextureSettings(TextureType.TEXTURE_2D, TextureWrapMode.REPEAT, TextureFilterMode.NEAREST, true), FileIO.readResourceFileContentBytes("textures/ui/buttons/build.png")).getTextureID();
			launchIconTextureId = new Texture2D(new TextureSettings(TextureType.TEXTURE_2D, TextureWrapMode.REPEAT, TextureFilterMode.NEAREST, true), FileIO.readResourceFileContentBytes("textures/ui/buttons/launch.png")).getTextureID();
			return true;
		} catch (IOException e) {
			Logger.error("EditorUI Failed to Initialize Editor Icons({}, {}, {}, {}, {}): {}", playIconTextureId, pauseIconTextureId, stopIconTextureId, buildIconTextureId, launchIconTextureId, e.getMessage());
			return false;
		}
	}

	private void renderToolBar() {
		float toolbarHeight = 48.0f;
		float iconSize = 32.0f;
		float spacing = 16.0f;

		ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 6, 6);
		ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, spacing, 0);
		ImGui.pushStyleVar(ImGuiStyleVar.FrameRounding, 4.0f);

		ImGui.pushStyleColor(ImGuiCol.Button, 0.25f, 0.25f, 0.25f, 1.0f);
		ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.35f, 0.35f, 0.35f, 1.0f);
		ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.45f, 0.45f, 0.45f, 1.0f);

		ImGui.beginChild("##toolbar", new ImVec2(0, toolbarHeight), false, ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse);
		float totalWidth = iconSize * 3 + spacing * 2;
		float startX = (ImGui.getContentRegionAvailX() - totalWidth) * 0.5f;
		ImGui.setCursorPosX(startX);

		// Play
		if (editorState == EditorState.PLAY) {
			ImGui.beginDisabled();
			ImGui.imageButton("##play", playIconTextureId, iconSize, iconSize);
			ImGui.endDisabled();
		} else {
			if (ImGui.imageButton("##play", playIconTextureId, iconSize, iconSize)) {
				EventBus.getInstance().emit(new EditorPlayEvent());
			}
		}
		if (ImGui.isItemHovered())
			ImGui.setTooltip("Play in Editor");

		ImGui.sameLine();

		// Pause
		if (editorState == EditorState.PLAY) {
			if (ImGui.imageButton("##pause", pauseIconTextureId, iconSize, iconSize)) {
				EventBus.getInstance().emit(new EditorPauseEvent());
			}
		} else {
			ImGui.beginDisabled();
			ImGui.imageButton("##pause", pauseIconTextureId, iconSize, iconSize);
			ImGui.endDisabled();
		}
		if (ImGui.isItemHovered())
			ImGui.setTooltip("Pause");

		ImGui.sameLine();

		// Stop
		if (editorState == EditorState.PLAY || editorState == EditorState.PAUSE) {
			if (ImGui.imageButton("##stop", stopIconTextureId, iconSize, iconSize)) {
				EventBus.getInstance().emit(new EditorStopEvent());
			}
		} else {
			ImGui.beginDisabled();
			ImGui.imageButton("##stop", stopIconTextureId, iconSize, iconSize);
			ImGui.endDisabled();
		}
		if (ImGui.isItemHovered())
			ImGui.setTooltip("Stop");

		ImGui.sameLine();

		// Build
		if (editorState == EditorState.STOP) {
			ImGui.beginDisabled();
			if (ImGui.imageButton("##build", buildIconTextureId, iconSize, iconSize)) {
				EventBus.getInstance().emit(new EditorBuildEvent());
			}
			ImGui.endDisabled();
		} else {
			ImGui.beginDisabled();
			ImGui.imageButton("##build", buildIconTextureId, iconSize, iconSize);
			ImGui.endDisabled();
		}
		if (ImGui.isItemHovered())
			ImGui.setTooltip("Build Project - UNDER DEVELOPMENT");

		ImGui.sameLine();

		// Launch
		if (editorState == EditorState.STOP) {
			ImGui.beginDisabled();
			if (ImGui.imageButton("##launch", launchIconTextureId, iconSize, iconSize)) {
				EventBus.getInstance().emit(new EditorLaunchEvent());
			}
			ImGui.endDisabled();
		} else {
			ImGui.beginDisabled();
			ImGui.imageButton("##launch", launchIconTextureId, iconSize, iconSize);
			ImGui.endDisabled();
		}
		if (ImGui.isItemHovered())
			ImGui.setTooltip("Launch Project in Runtime - UNDER DEVELOPMENT");

		ImGui.endChild();
		ImGui.popStyleColor(3);
		ImGui.popStyleVar(3);
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
		renderToolBar();

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
