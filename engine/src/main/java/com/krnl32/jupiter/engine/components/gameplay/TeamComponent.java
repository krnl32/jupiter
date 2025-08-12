package com.krnl32.jupiter.engine.components.gameplay;

import com.krnl32.jupiter.engine.ecs.Component;

public class TeamComponent implements Component {
	public int teamId;

	public TeamComponent(int teamId) {
		this.teamId = teamId;
	}
}
