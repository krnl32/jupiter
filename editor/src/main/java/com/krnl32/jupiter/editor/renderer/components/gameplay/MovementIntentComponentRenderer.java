package com.krnl32.jupiter.editor.renderer.components.gameplay;

import com.krnl32.jupiter.editor.renderer.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.components.gameplay.MovementIntentComponent;
import imgui.ImGui;
import imgui.type.ImBoolean;

public class MovementIntentComponentRenderer implements ComponentRenderer<MovementIntentComponent> {
	@Override
	public void render(MovementIntentComponent component) {
		GUIUtils.renderVector3f("Translation", component.translation);
		GUIUtils.renderVector3f("Rotation", component.rotation);

		ImBoolean jump = new ImBoolean(component.jump);
		if (ImGui.checkbox("Jump", jump)) {
			component.jump = jump.get();
		}

		ImBoolean sprint = new ImBoolean(component.sprint);
		if (ImGui.checkbox("Sprint", sprint)) {
			component.sprint = sprint.get();
		}
	}
}
