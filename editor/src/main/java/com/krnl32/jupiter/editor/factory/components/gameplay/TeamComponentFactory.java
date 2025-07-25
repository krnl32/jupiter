package com.krnl32.jupiter.editor.factory.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.TeamComponent;
import com.krnl32.jupiter.editor.factory.ComponentFactory;

public class TeamComponentFactory implements ComponentFactory<TeamComponent> {
	@Override
	public TeamComponent create() {
		return new TeamComponent(-1);
	}
}
