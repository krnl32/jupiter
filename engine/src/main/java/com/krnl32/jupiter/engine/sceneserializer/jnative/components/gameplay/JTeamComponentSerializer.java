package com.krnl32.jupiter.engine.sceneserializer.jnative.components.gameplay;

import com.krnl32.jupiter.engine.components.gameplay.TeamComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;

/*
 * == TeamComponent (size: 4 Bytes) ==
 * 4 Bytes	TeamID	uint32
 */
public class JTeamComponentSerializer implements ComponentSerializer<TeamComponent, byte[]> {
	private static final int SIZE = 4;

	@Override
	public byte[] serialize(TeamComponent component) {
		return JSerializerUtility.serializeInt(component.teamId);
	}

	@Override
	public TeamComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JTeamComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		int teamId = JSerializerUtility.deserializeInt(data);

		return new TeamComponent(teamId);
	}
}
