package com.krnl32.jupiter.launcher.panels;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.platform.FileDialog;
import com.krnl32.jupiter.engine.project.ProjectLoader;
import com.krnl32.jupiter.engine.project.model.Project;
import com.krnl32.jupiter.launcher.events.ui.NewProjectPanelEvent;
import com.krnl32.jupiter.launcher.launcher.UIPanel;
import com.krnl32.jupiter.launcher.project.JProject;
import com.krnl32.jupiter.launcher.project.ProjectManager;
import imgui.ImGui;
import imgui.flag.ImGuiTableFlags;

public class ProjectListPanel implements UIPanel {
	private final ProjectManager projectManager;

	public ProjectListPanel(ProjectManager projectManager) {
		this.projectManager = projectManager;
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
			String path = FileDialog.openFolder();
			if (path != null && !path.isEmpty()) {
				Project project = ProjectLoader.load(path);
				if (project != null) {
					projectManager.addProject(new JProject(project.getInfo().getProjectName(), path, project.getInfo().getEngineVersion(), project.getInfo().getTemplate()));
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

		ImGui.end();
	}

	private void launchProject(JProject project) {
		// launch editor with project path
	}
}
