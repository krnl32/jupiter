package com.krnl32.jupiter.serializer.components;

import com.krnl32.jupiter.components.TeamComponent;
import com.krnl32.jupiter.serializer.ComponentSerializer;
import com.krnl32.jupiter.serializer.resolvers.EntityResolver;
import org.json.JSONObject;

public class TeamComponentSerializer implements ComponentSerializer<TeamComponent> {
	@Override
	public JSONObject serialize(TeamComponent component) {
		return new JSONObject().put("teamID", component.teamID);
	}

	@Override
	public TeamComponent deserialize(JSONObject data, EntityResolver resolver) {
		return new TeamComponent(data.getInt("teamID"));
	}

	@Override
	public TeamComponent clone(TeamComponent component) {
		return new TeamComponent(component.teamID);
	}
}
