package com.krnl32.jupiter.factory.components.gameplay;

import com.krnl32.jupiter.components.gameplay.TeamComponent;
import com.krnl32.jupiter.factory.ComponentFactory;

public class TeamComponentFactory implements ComponentFactory<TeamComponent> {
	@Override
	public TeamComponent create() {
		return new TeamComponent(-1);
	}
}
