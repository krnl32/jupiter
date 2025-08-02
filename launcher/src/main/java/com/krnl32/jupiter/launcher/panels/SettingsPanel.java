package com.krnl32.jupiter.launcher.panels;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.platform.FileDialog;
import com.krnl32.jupiter.launcher.editor.EditorManager;
import com.krnl32.jupiter.launcher.editor.EditorUtil;
import com.krnl32.jupiter.launcher.editor.JEditor;
import com.krnl32.jupiter.launcher.events.ui.OpenSettingsPanelEvent;
import com.krnl32.jupiter.launcher.launcher.UIPanel;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTabBarFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SettingsPanel implements UIPanel {
	private boolean showSettingsPanel;
	private final EditorManager editorManager;
	private final ImInt selectedEditorIndex;

	public SettingsPanel(EditorManager editorManager) {
		this.editorManager = editorManager;
		this.selectedEditorIndex = new ImInt(-1);

		EventBus.getInstance().register(OpenSettingsPanelEvent.class, event -> {
			this.showSettingsPanel = true;
		});
	}

	@Override
	public void onUpdate(float dt) {}

	@Override
	public void onRender(float dt) {
		if (showSettingsPanel) {
			ImGui.openPopup("Settings");
			showSettingsPanel = false;
		}

		if (ImGui.beginPopupModal("Settings", ImGuiWindowFlags.AlwaysAutoResize)) {
			ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 10.0f, 8.0f);

			if (ImGui.beginTabBar("SettingsTabBar", ImGuiTabBarFlags.None)) {
				if (ImGui.beginTabItem("Editor")) {
					renderEditorTab();
					ImGui.endTabItem();
				}
				ImGui.endTabBar();
			}

			ImGui.spacing();
			ImGui.separator();
			ImGui.spacing();

			if (ImGui.button("Close")) {
				ImGui.closeCurrentPopup();
			}

			ImGui.popStyleVar();
			ImGui.endPopup();
		}
	}

	private void renderEditorTab() {
		ImGui.text("Manage Editors:");
		ImGui.separator();

		List<JEditor> editors = editorManager.getEditors();

		// Editors List
		String[] editorLabels = new String[editors.size()];
		for (int i = 0; i < editors.size(); i++) {
			JEditor editor = editors.get(i);
			editorLabels[i] = "[v" + editor.getVersion() + "] " + editor.getPath();
			if (editor.getPath().equals(editorManager.getSelectedEditorPath())) {
				selectedEditorIndex.set(i);
			}
		}

		// Dropdown
		ImGui.text("Editor Versions");
		ImGui.sameLine();
		if (ImGui.combo("##EditorVersions", selectedEditorIndex, editorLabels, editorLabels.length)) {
			if (selectedEditorIndex.get() >= 0 && selectedEditorIndex.get() < editors.size()) {
				editorManager.setSelectedEditorPath(editors.get(selectedEditorIndex.get()).getPath());
			}
		}

		ImGui.spacing();

		// Add New Editor
		if (ImGui.button("Add Editor (.jar)")) {
			String path = FileDialog.openFile(".jar");

			try {
				if (path != null && !path.isEmpty() && Files.exists(Path.of(path))) {
					editorManager.addEditor(new JEditor(path, EditorUtil.extractVersionFromEditorPath(path)));
					editorManager.setSelectedEditorPath(path);
				} else {
					Logger.error("SettingsPanel Failed to Add Editor Invalid Path({})", path);
				}
			} catch (Exception e) {
				Logger.error("SettingsPanel Failed to Add Editor Invalid Path({}): {}", path, e.getMessage());
			}
		}

		ImGui.sameLine();

		// Remove Selected Editor
		if (ImGui.button("Remove Selected") && selectedEditorIndex.get() >= 0) {
			editorManager.removeEditor(editors.get(selectedEditorIndex.get()));
			selectedEditorIndex.set(-1);
		}

		ImGui.spacing();

		JEditor selected = editorManager.getSelectedEditor();
		if (selected != null) {
			ImGui.textColored(0.2f, 0.8f, 0.2f, 1.0f, "✔ Selected: " + selected.getPath());
		} else {
			ImGui.textColored(0.9f, 0.2f, 0.2f, 1.0f, "✘ No editor selected.");
		}
	}
}
