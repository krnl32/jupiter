package com.krnl32.jupiter.launcher.panels;

import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.launcher.events.ui.NewProjectPanelEvent;
import com.krnl32.jupiter.launcher.launcher.UIPanel;
import com.krnl32.jupiter.launcher.project.Project;
import com.krnl32.jupiter.launcher.project.ProjectManager;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;
import imgui.type.ImString;

public class NewProjectPanel implements UIPanel {
	private final ProjectManager projectManager;
	private final ImString newProjectName;
	private final ImString newProjectPath;
	private final String[] engineVersions;
	private final String[] templates;
	private ImInt selectedEngineVersion, selectedTemplate;
	private boolean showNewProjectPanel;

	public NewProjectPanel(ProjectManager projectManager) {
		this.projectManager = projectManager;
		this.newProjectName = new ImString(256);
		this.newProjectPath = new ImString(256);
		this.engineVersions = new String[] {"0.0.1", "0.0.2"};
		this.templates = new String[]{"2D", "3D"};
		this.selectedEngineVersion = new ImInt(0);
		this.selectedTemplate = new ImInt(0);

		EventBus.getInstance().register(NewProjectPanelEvent.class, event -> {
			this.showNewProjectPanel = true;
		});
	}

	@Override
	public void onUpdate(float dt) {

	}

	@Override
	public void onRender(float dt) {
		if (showNewProjectPanel) {
			ImGui.openPopup("Create New Project");
			showNewProjectPanel = false;
		}

		if (ImGui.beginPopupModal("Create New Project", ImGuiWindowFlags.AlwaysAutoResize)) {

			ImGui.inputText("Project Name", newProjectName);
			ImGui.inputText("Project Path", newProjectPath);

			ImGui.text("Template");
			ImGui.combo("##template", selectedTemplate, templates);

			ImGui.text("Engine Version");
			ImGui.combo("##version", selectedEngineVersion, engineVersions);

			if (ImGui.button("Create")) {
				String name = newProjectName.toString().trim();
				String path = newProjectPath.toString().trim();
				if (!name.isEmpty() && !path.isEmpty()) {
					Project newProject = new Project(name, path, engineVersions[selectedEngineVersion.get()]);
					projectManager.addProject(newProject);
					generateProjectFiles(newProject, templates[selectedTemplate.get()]);
				}
				ImGui.closeCurrentPopup();
			}

			ImGui.sameLine();
			if (ImGui.button("Cancel")) {
				ImGui.closeCurrentPopup();
			}

			ImGui.endPopup();
		}
	}

	private void generateProjectFiles(Project project, String template) {
		// Generate Project Files
	}
}
