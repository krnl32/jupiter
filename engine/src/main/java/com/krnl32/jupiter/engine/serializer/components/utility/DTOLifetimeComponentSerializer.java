package com.krnl32.jupiter.engine.serializer.components.utility;

import com.krnl32.jupiter.engine.components.utility.LifetimeComponent;
import com.krnl32.jupiter.engine.serializer.ComponentSerializer;
import com.krnl32.jupiter.engine.serializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.serializer.utility.DTOComponentSerializerUtility;

import java.util.Map;

public class DTOLifetimeComponentSerializer implements ComponentSerializer<LifetimeComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(LifetimeComponent component) {
		return Map.of("remainingTime", component.remainingTime);
	}

	@Override
	public LifetimeComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		return new LifetimeComponent(DTOComponentSerializerUtility.convertToFloat(data.get("remainingTime")));
	}
}
