package com.krnl32.jupiter.launcher.panels;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.platform.FileDialog;
import com.krnl32.jupiter.engine.project.ProjectLoader;
import com.krnl32.jupiter.engine.project.model.Project;
import com.krnl32.jupiter.launcher.editor.EditorManager;
import com.krnl32.jupiter.launcher.events.ui.NewProjectPanelEvent;
import com.krnl32.jupiter.launcher.launcher.UIPanel;
import com.krnl32.jupiter.launcher.project.JProject;
import com.krnl32.jupiter.launcher.project.ProjectManager;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiTableFlags;

import java.nio.file.Path;

public class ProjectListPanel implements UIPanel {
	private final ProjectManager projectManager;
	private final EditorManager editorManager;

	public ProjectListPanel(ProjectManager projectManager, EditorManager editorManager) {
		this.projectManager = projectManager;
		this.editorManager = editorManager;
	}

	@Override
	public void onUpdate(float dt) {
	}

	@Override
	public void onRender(float dt) {
		ImGui.begin("Project Launcher");

		if (ImGui.button("New Project")) {
			EventBus.getInstance().emit(new NewProjectPanelEvent());
		}

		ImGui.sameLine();
		if (ImGui.button("Import Project")) {
			Path path = FileDialog.openFolder();
			if (path != null) {
				Project project = ProjectLoader.load(path);
				if (project != null) {
					String name = project.getInfo().getProjectName();
					String engineVersion = project.getInfo().getEngineVersion();
					String template = project.getInfo().getTemplate();
					projectManager.addProject(new JProject(name, path.toString(), engineVersion, template));
				} else {
					Logger.error("ProjectListPanel failed to Import Project({})", path);
				}
			}
		}

		ImGui.sameLine();
		if (ImGui.button("Refresh")) {
			projectManager.reloadProjects();
		}

		ImGui.separator();

		if (ImGui.beginTable("projects", 5, ImGuiTableFlags.Borders | ImGuiTableFlags.RowBg)) {
			ImGui.tableSetupColumn("Name");
			ImGui.tableSetupColumn("Path");
			ImGui.tableSetupColumn("EngineVersion");
			ImGui.tableSetupColumn("Template");
			ImGui.tableSetupColumn("Action");
			ImGui.tableHeadersRow();

			for (JProject project : projectManager.getProjects()) {
				ImGui.tableNextRow();
				ImGui.tableSetColumnIndex(0);
				ImGui.text(project.getName());

				ImGui.tableSetColumnIndex(1);
				ImGui.textWrapped(project.getPath());

				ImGui.tableSetColumnIndex(2);
				ImGui.text(project.getEngineVersion());

				ImGui.tableSetColumnIndex(3);
				ImGui.text(project.getTemplate());

				ImGui.tableSetColumnIndex(4);
				if (ImGui.button("launch##" + project.getName())) {
					launchProject(project);
				}

				ImGui.sameLine();

				if (ImGui.button("remove##" + project.getName())) {
					projectManager.removeProject(project);
					break;
				}
			}

			ImGui.endTable();
		}

		// Editor Selection Warning
		float padding = 10.0f;
		ImVec2 windowSize = ImGui.getWindowSize();
		ImVec2 textSize;
		String message;

		if (editorManager.getSelectedEditorPath() == null) {
			message = "No Editor Selected";
			textSize = ImGui.calcTextSize(message);
			ImGui.setCursorPos(new ImVec2(windowSize.x - textSize.x - padding, windowSize.y - textSize.y - padding));
			ImGui.pushStyleColor(ImGuiCol.Text, 1.0f, 0.0f, 0.0f, 1.0f); // Red
			ImGui.text(message);
			ImGui.popStyleColor();
		} else {
			String path = editorManager.getSelectedEditorPath();
			String version = editorManager.getSelectedEditor().getVersion();
			message = String.format("Editor: %s (v%s)", path, version);
			textSize = ImGui.calcTextSize(message);
			ImGui.setCursorPos(new ImVec2(windowSize.x - textSize.x - padding, windowSize.y - textSize.y - padding));
			ImGui.pushStyleColor(ImGuiCol.Text, 0.0f, 1.0f, 0.0f, 1.0f); // Green
			ImGui.text(message);
			ImGui.popStyleColor();
		}

		ImGui.end();
	}

	private void launchProject(JProject project) {
		if (editorManager.getSelectedEditorPath() == null) {
			Logger.error("Launcher Failed to Launch Project({}): No Editor Selected", project.getName());
			return;
		}

		String editorJar = editorManager.getSelectedEditorPath();
		String projectPath = project.getPath();

		try {
			new ProcessBuilder("java", "-jar", editorJar, "--launch", projectPath)
				.inheritIO()
				.start();
		} catch (Exception e) {
			Logger.error("Launcher Failed to Launch Project({}): {}", project.getName(), e.getMessage());
		}
	}
}
