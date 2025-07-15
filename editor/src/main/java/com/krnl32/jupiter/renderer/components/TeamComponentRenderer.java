package com.krnl32.jupiter.renderer.components;

import com.krnl32.jupiter.components.TeamComponent;
import com.krnl32.jupiter.renderer.ComponentRenderer;
import com.krnl32.jupiter.utility.GUIUtils;
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
