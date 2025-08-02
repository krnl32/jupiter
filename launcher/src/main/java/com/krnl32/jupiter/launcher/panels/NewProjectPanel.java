package com.krnl32.jupiter.launcher.panels;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.platform.FileDialog;
import com.krnl32.jupiter.launcher.events.ui.NewProjectPanelEvent;
import com.krnl32.jupiter.launcher.launcher.UIPanel;
import com.krnl32.jupiter.launcher.project.JProject;
import com.krnl32.jupiter.launcher.project.ProjectInitializer;
import com.krnl32.jupiter.launcher.project.ProjectManager;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;
import imgui.type.ImString;

import java.nio.file.Files;
import java.nio.file.Path;

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
		this.engineVersions = new String[] {"0.0.1"};
		this.templates = new String[]{"2D"};
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
			ImGui.text("Project Name");
			ImGui.inputText("##projectName", newProjectName);

			ImGui.text("Project Path");
			ImGui.inputText("##projectPath", newProjectPath);
			ImGui.sameLine();
			if (ImGui.button("Browse")) {
				String path = FileDialog.openFolder();
				if (path != null && !path.isEmpty()) {
					newProjectPath.set(path);
				}
			}

			ImGui.text("Template");
			ImGui.combo("##template", selectedTemplate, templates);

			ImGui.text("Engine Version");
			ImGui.combo("##version", selectedEngineVersion, engineVersions);

			if (ImGui.button("Create")) {
				String name = newProjectName.toString().trim();
				String path = newProjectPath.toString().trim();
				if (!name.isEmpty() && !path.isEmpty() && Files.exists(Path.of(path))) {
					JProject newProject = ProjectInitializer.createProject(name, path, engineVersions[selectedEngineVersion.get()], templates[selectedTemplate.get()]);
					if (newProject != null) {
						projectManager.addProject(newProject);
					} else {
						Logger.error("NewProjectPanel failed to Create New Project({},{},{},{})", name, path, engineVersions[selectedEngineVersion.get()], templates[selectedTemplate.get()]);
					}
				} else {
					Logger.error("NewProjectPanel failed to Create New Project({},{},{},{}), Invalid Path", name, path, engineVersions[selectedEngineVersion.get()], templates[selectedTemplate.get()]);
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
}
