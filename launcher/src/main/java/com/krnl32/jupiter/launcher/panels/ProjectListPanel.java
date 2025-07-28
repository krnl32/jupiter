package com.krnl32.jupiter.launcher.panels;

import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.launcher.events.ui.NewProjectPanelEvent;
import com.krnl32.jupiter.launcher.launcher.UIPanel;
import com.krnl32.jupiter.launcher.project.Project;
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
		if (ImGui.button("Refresh")) {
			projectManager.reloadProjects();
		}

		ImGui.separator();

		if (ImGui.beginTable("projects", 4, ImGuiTableFlags.Borders | ImGuiTableFlags.RowBg)) {
			ImGui.tableSetupColumn("Name");
			ImGui.tableSetupColumn("Path");
			ImGui.tableSetupColumn("Version");
			ImGui.tableSetupColumn("Action");
			ImGui.tableHeadersRow();

			for (Project project : projectManager.getProjects()) {
				ImGui.tableNextRow();
				ImGui.tableSetColumnIndex(0);
				ImGui.text(project.getName());

				ImGui.tableSetColumnIndex(1);
				ImGui.textWrapped(project.getPath());

				ImGui.tableSetColumnIndex(2);
				ImGui.text(project.getEngineVersion());

				ImGui.tableSetColumnIndex(3);
				if (ImGui.button("launch##" + project.getName())) {
					launchProject(project);
				}
			}

			ImGui.endTable();
		}

		ImGui.end();
	}

	private void launchProject(Project project) {
		// launch editor with project path
	}
}
