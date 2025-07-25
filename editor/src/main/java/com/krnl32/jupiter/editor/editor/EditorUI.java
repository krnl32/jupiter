package com.krnl32.jupiter.editor.editor;

import com.krnl32.jupiter.engine.asset.AssetManager;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.core.Window;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.editor.events.editor.EditorPauseEvent;
import com.krnl32.jupiter.editor.events.editor.EditorPlayEvent;
import com.krnl32.jupiter.editor.events.editor.EditorStopEvent;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.*;
import imgui.type.ImBoolean;

import java.util.ArrayList;
import java.util.List;

public class EditorUI {
	private final ImGuiWrapper imGuiWrapper;
	private final List<EditorPanel> editorPanels;
	private EditorState editorState;

	public EditorUI(Window window) {
		this.imGuiWrapper = new ImGuiWrapper(window);
		this.editorPanels = new ArrayList<>();
		this.editorState = EditorState.STOP;

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

		int playIcon = ((TextureAsset) AssetManager.getInstance().getAsset("editorPlayButton")).getTexture().getTextureID();
		int pauseIcon = ((TextureAsset) AssetManager.getInstance().getAsset("editorPauseButton")).getTexture().getTextureID();
		int stopIcon = ((TextureAsset) AssetManager.getInstance().getAsset("editorStopButton")).getTexture().getTextureID();

		// Play
		if (editorState == EditorState.PLAY) {
			ImGui.beginDisabled();
			ImGui.imageButton("##play", playIcon, iconSize, iconSize);
			ImGui.endDisabled();
		} else {
			if (ImGui.imageButton("##play", playIcon, iconSize, iconSize)) {
				EventBus.getInstance().emit(new EditorPlayEvent());
			}
		}
		if (ImGui.isItemHovered())
			ImGui.setTooltip("Play");

		ImGui.sameLine();

		// Pause
		if (editorState == EditorState.PLAY) {
			if (ImGui.imageButton("##pause", pauseIcon, iconSize, iconSize)) {
				EventBus.getInstance().emit(new EditorPauseEvent());
			}
		} else {
			ImGui.beginDisabled();
			ImGui.imageButton("##pause", pauseIcon, iconSize, iconSize);
			ImGui.endDisabled();
		}
		if (ImGui.isItemHovered())
			ImGui.setTooltip("Pause");

		ImGui.sameLine();

		// Stop
		if (editorState == EditorState.PLAY || editorState == EditorState.PAUSE) {
			if (ImGui.imageButton("##stop", stopIcon, iconSize, iconSize)) {
				EventBus.getInstance().emit(new EditorStopEvent());
			}
		} else {
			ImGui.beginDisabled();
			ImGui.imageButton("##stop", stopIcon, iconSize, iconSize);
			ImGui.endDisabled();
		}
		if (ImGui.isItemHovered())
			ImGui.setTooltip("Stop");

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
