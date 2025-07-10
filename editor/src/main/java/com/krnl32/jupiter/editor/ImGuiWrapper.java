package com.krnl32.jupiter.editor;

import com.krnl32.jupiter.core.Window;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public class ImGuiWrapper {
	private ImGuiImplGlfw imGuiImplGlfw;
	private ImGuiImplGl3 imGuiImplGl3;

	public ImGuiWrapper(Window window) {
		ImGui.createContext();
		imGuiImplGlfw = new ImGuiImplGlfw();
		imGuiImplGl3 = new ImGuiImplGl3();

		ImGui.getIO().setDisplaySize(new ImVec2(window.getWidth(), window.getHeight()));
		ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
		ImGui.getIO().addConfigFlags(ImGuiConfigFlags.DockingEnable);
		ImGui.getIO().addConfigFlags(ImGuiConfigFlags.ViewportsEnable);

		ImGui.styleColorsDark();
		imGuiImplGlfw.init(window.getNativeWindow(), true);
		imGuiImplGl3.init("#version 460 core");
	}

	public void destroy() {
		imGuiImplGl3.shutdown();
		imGuiImplGlfw.shutdown();
		ImGui.destroyContext();
	}

	public void beginFrame() {
		imGuiImplGl3.newFrame();
		imGuiImplGlfw.newFrame();
		ImGui.newFrame();
	}

	public void endFrame() {
		ImGui.render();
		imGuiImplGl3.renderDrawData(ImGui.getDrawData());

		if ((ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) || (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.DockingEnable))) {
			long backupWindowPtr = glfwGetCurrentContext();
			ImGui.updatePlatformWindows();
			ImGui.renderPlatformWindowsDefault();
			glfwMakeContextCurrent(backupWindowPtr);
		}
	}
}
