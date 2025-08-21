package com.krnl32.jupiter.engine.sceneserializer.data.components.effects;

import com.krnl32.jupiter.engine.components.effects.ParticleComponent;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataParticleComponentSerializer implements ComponentSerializer<ParticleComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(ParticleComponent component) {
		return Map.of(
			"velocity", DataSerializerUtility.serializeVector3f(component.velocity),
			"duration", component.duration,
			"remainingTime", component.remainingTime
		);
	}

	@Override
	public ParticleComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new ParticleComponent(
			DataSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("velocity")),
			DataSerializerUtility.toFloat(data.get("duration")),
			DataSerializerUtility.toFloat(data.get("remainingTime"))
		);
	}
}
