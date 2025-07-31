package com.krnl32.jupiter.editor.editor;

import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.core.Window;
import com.krnl32.jupiter.engine.utility.FileIO;
import imgui.*;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import java.nio.file.Path;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public class ImGuiWrapper {
	private ImGuiImplGlfw imGuiImplGlfw;
	private ImGuiImplGl3 imGuiImplGl3;

	public ImGuiWrapper(Window window) {
		ImGui.createContext();

		// Load imgui.ini (Temporary, Instead -> Create User Data Config)
		String imguiINIPath = Path.of((System.getProperty("project.resource") != null) ? System.getProperty("project.resource") + "/Settings/imgui.ini" : System.getProperty("user.dir") + "/editor/src/main/resources/Settings/imgui.ini").toString();
		ImGui.getIO().setIniFilename(imguiINIPath);

		imGuiImplGlfw = new ImGuiImplGlfw();
		imGuiImplGl3 = new ImGuiImplGl3();

		ImGui.getIO().setDisplaySize(new ImVec2(window.getWidth(), window.getHeight()));
		ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
		ImGui.getIO().addConfigFlags(ImGuiConfigFlags.DockingEnable);
		ImGui.getIO().addConfigFlags(ImGuiConfigFlags.ViewportsEnable);

		// Setup Fonts
		ImFontAtlas fonts = ImGui.getIO().getFonts();
		try {
			ImFontConfig config = new ImFontConfig();
			config.setMergeMode(false);
			ImFont JetBrainsMonoRegularFont = fonts.addFontFromMemoryTTF(FileIO.readResourceFileContentBytes("Assets/Fonts/JetBrainsMono-Regular.ttf"), 16, config);
			config.setMergeMode(true);
			fonts.addFontFromMemoryTTF(FileIO.readResourceFileContentBytes("Assets/Fonts/fa-solid-900.ttf"), 16, config, fonts.getGlyphRangesDefault());
			ImGui.getIO().setFontDefault(JetBrainsMonoRegularFont);
		} catch (Exception e) {
			Logger.critical("ImGuiWrapper Failed to Load Fonts: {}", e.getMessage());
			return;
		}
		fonts.build();

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
		imGuiImplGlfw.newFrame();
		imGuiImplGl3.newFrame();
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
