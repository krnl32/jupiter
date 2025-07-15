package com.krnl32.jupiter.components.gameplay;

import com.krnl32.jupiter.ecs.Component;

public class TeamComponent implements Component {
	public int teamID;

	public TeamComponent(int teamID) {
		this.teamID = teamID;
	}
}
