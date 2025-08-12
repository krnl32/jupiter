package com.krnl32.jupiter.engine.serializer.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.TeamComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;

import java.util.Map;

public class DTOTeamComponentSerializer implements ComponentSerializer<TeamComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(TeamComponent component) {
		return Map.of("teamID", component.teamId);
	}

	@Override
	public TeamComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new TeamComponent((int) data.get("teamID"));
	}
}
