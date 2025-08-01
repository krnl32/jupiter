package com.krnl32.jupiter.launcher.launcher;

import com.krnl32.jupiter.engine.core.Engine;
import com.krnl32.jupiter.engine.renderer.FrameBufferAttachmentFormat;
import com.krnl32.jupiter.engine.renderer.Framebuffer;
import com.krnl32.jupiter.engine.renderer.Renderer;
import com.krnl32.jupiter.launcher.panels.NewProjectPanel;
import com.krnl32.jupiter.launcher.panels.ProjectListPanel;
import com.krnl32.jupiter.launcher.project.ProjectManager;

import java.util.List;

public class Launcher extends Engine {
	private Framebuffer framebuffer;
	private ProjectManager projectManager;
	private LauncherUI launcherUI;

	public Launcher(String name, int width, int height) {
		super(name, width, height);
	}

	@Override
	public boolean onInit() {
		// FrameBuffer
		framebuffer = new Framebuffer(getWindow().getWidth(), getWindow().getHeight(), List.of(FrameBufferAttachmentFormat.RGBA8, FrameBufferAttachmentFormat.RED_INTEGER));
		getRenderer().setFramebuffer(framebuffer);

		// Launcher UI
		projectManager = new ProjectManager();
		launcherUI = new LauncherUI(getWindow());
		launcherUI.addUIPanel(new ProjectListPanel(projectManager));
		launcherUI.addUIPanel(new NewProjectPanel(projectManager));
		return true;
	}

	@Override
	public void onUpdate(float dt) {
		launcherUI.onUpdate(dt);
		launcherUI.onRender(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
	}
}
