package com.krnl32.jupiter.engine.sceneserializer.data.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.TeamComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

import java.util.Map;

public class DataTeamComponentSerializer implements ComponentSerializer<TeamComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(TeamComponent component) {
		return Map.of("teamID", component.teamId);
	}

	@Override
	public TeamComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new TeamComponent((int) data.get("teamID"));
	}
}
