package com.krnl32.jupiter.editor.renderer.components.gameplay;

import com.krnl32.jupiter.editor.renderer.ComponentRenderer;
import com.krnl32.jupiter.editor.utility.GUIUtils;
import com.krnl32.jupiter.engine.components.gameplay.TeamComponent;
import imgui.type.ImInt;

public class TeamComponentRenderer implements ComponentRenderer<TeamComponent> {
	@Override
	public void render(TeamComponent component) {
		ImInt teamId = new ImInt(component.teamID);
		if (GUIUtils.renderIntInputWithResetButton("Team ID", teamId, -1)) {
			component.teamID = teamId.get();
		}
	}
}
