package com.krnl32.jupiter.engine.serializer.components.effects;

import com.krnl32.jupiter.engine.components.effects.ParticleComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;

public class DTOParticleComponentSerializer implements ComponentSerializer<ParticleComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(ParticleComponent component) {
		return Map.of(
			"velocity", DTOComponentSerializerUtility.serializeVector3f(component.velocity),
			"duration", component.duration,
			"remainingTime", component.remainingTime
		);
	}

	@Override
	public ParticleComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new ParticleComponent(
			DTOComponentSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("velocity")),
			DTOComponentSerializerUtility.toFloat(data.get("duration")),
			DTOComponentSerializerUtility.toFloat(data.get("remainingTime"))
		);
	}
}
