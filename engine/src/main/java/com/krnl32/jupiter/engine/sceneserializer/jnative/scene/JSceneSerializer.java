package com.krnl32.jupiter.engine.sceneserializer.jnative.scene;

import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Component;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.scene.SceneSettings;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializerRegistry;
import com.krnl32.jupiter.engine.sceneserializer.ComponentType;
import com.krnl32.jupiter.engine.sceneserializer.SceneSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.utility.ByteUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JSceneSerializer implements SceneSerializer<byte[]> {
	private final ComponentSerializerRegistry componentSerializerRegistry;

	public JSceneSerializer(ComponentSerializerRegistry componentSerializerRegistry) {
		this.componentSerializerRegistry = componentSerializerRegistry;
	}

	@Override
	public byte[] serialize(Scene scene) {
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

		try {
			// Scene METADATA Scene Name
			byte[] sceneNameBytes = scene.getName().getBytes(StandardCharsets.UTF_8);

			if (sceneNameBytes.length > 255) {
				Logger.error("JSceneSerializer Serialize failed, Invalid Scene Name Size > 255({})", sceneNameBytes.length);
				return null;
			}

			dataStream.write(sceneNameBytes.length);
			dataStream.write(sceneNameBytes);

			// Scene METADATA Entity Count
			int entityCount = scene.getRegistry().getEntities().size();
			ByteUtils.writeUInt32LE(dataStream, entityCount);

			// Scene ENTITY/COMPONENT
			for (Entity entity : scene.getRegistry().getEntities()) {
				serializeEntity(entity, dataStream);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return dataStream.toByteArray();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Scene deserialize(byte[] data, SceneSettings settings) {
		ByteArrayInputStream dataStream = new ByteArrayInputStream(data);

		// Scene METADATA Scene Name
		int sceneNameLength = dataStream.read();

		byte[] sceneNameBytes = new byte[sceneNameLength];
		int readLength = dataStream.read(sceneNameBytes, 0, sceneNameLength);

		if (readLength != sceneNameLength) {
			Logger.error("JSceneSerializer Deserialize failed, Invalid Scene Name Size > 255({})", readLength);
			return null;
		}

		String sceneName = new String(sceneNameBytes, 0, sceneNameLength, StandardCharsets.UTF_8);

		Scene scene = new Scene(sceneName, settings) {
			@Override
			public void onCreate() {}
			@Override
			public void onActivate() {}
			@Override
			public void onUnload() {}
		};

		// Scene METADATA Entity Count
		int entityCount = ByteUtils.readUint32LE(dataStream);

		// Create Entities with UUIDComponent
		Map<UUID, Entity> uuidToEntity = new HashMap<>();

		byte[] invalidUUID = new byte[16];

		for (int i = 0; i < entityCount; i++) {
			byte[] uuidData = new byte[16];
			dataStream.read(uuidData, 0, 16);

			if (Arrays.equals(uuidData, invalidUUID)) {
				Logger.error("SceneSerializer deserialize Failed, Invalid Entity UUID");
				return null;
			}

			UUID entityUUID = JSerializerUtility.deserializeUUID(uuidData);
			int componentCount = ByteUtils.readUint32LE(dataStream);

			UUID uuid = new UUID(entityUUID.getMostSignificantBits(), entityUUID.getLeastSignificantBits());
			Entity entity = scene.createEntity();
			entity.addComponent(new UUIDComponent(uuid));
			uuidToEntity.put(uuid, entity);

			// Skip to Next Entity
			for (int j = 0; j < componentCount; j++) {
				dataStream.skip(1); // Skip Component Type
				int componentSize = ByteUtils.readUint32LE(dataStream);
				dataStream.skip(componentSize); // Skip Component Data
			}
		}

		// Resolve UUIDs
		EntityResolver resolver = uuid -> {
			Entity entity = uuidToEntity.get(uuid);
			if (entity == null) {
				Logger.error("SceneSerializer Deserialize Failed, Entity Not Found for UUID({})", uuid);
			}
			return entity;
		};

		// Skip METADATA
		dataStream.reset();
		dataStream.skip(5 + sceneNameLength);

		// Deserialize ENTITY/COMPONENT
		for (int i = 0; i < entityCount; i++) {
			deserializeEntity(dataStream, resolver, uuidToEntity);
		}

		return scene;
	}

	private void serializeEntity(Entity entity, ByteArrayOutputStream dataStream) throws IOException {
		UUIDComponent uuidComponent = entity.getComponent(UUIDComponent.class);

		if (uuidComponent == null) {
			Logger.error("SceneSerializer Serialize Failed, UUIDComponent Not Found for Entity({})", entity.getTagOrId());
			return;
		}

		// Serialize UUID
		byte[] uuid = JSerializerUtility.serializeUUID(uuidComponent.uuid);
		dataStream.write(uuid);

		// Serialize Entity Component Count
		int componentCount = entity.getComponents().size();
		ByteUtils.writeUInt32LE(dataStream, componentCount);

		// Serialize Components
		for (Component component : entity.getComponents()) {
			ComponentType componentType = ComponentType.valueOf(component.getClass().getSimpleName());
			ByteUtils.writeUInt32LE(dataStream, componentType.getId());

			ComponentSerializer<Component, byte[]> componentSerializer = componentSerializerRegistry.getSerializer(component.getClass());

			if (componentSerializer == null) {
				Logger.warn("SceneSerializer serializeEntity Failed, ComponentSerializer({}) Not Found, Skipping Component...", component.getClass().getSimpleName());
				continue;
			}

			byte[] componentData = componentSerializer.serialize(component);
			int componentSize = componentData.length;

			ByteUtils.writeUInt32LE(dataStream, componentSize);
			dataStream.write(componentData);
		}
	}

	@SuppressWarnings("unchecked")
	private void deserializeEntity(ByteArrayInputStream dataStream, EntityResolver resolver, Map<UUID, Entity> uuidToEntity) {
		byte[] uuidData = new byte[16];
		int readLen = dataStream.read(uuidData, 0, 16);

		if (readLen != 16 || Arrays.equals(uuidData, new byte[16])) {
			Logger.error("SceneSerializer deserializeEntity Failed, Invalid Entity UUID");
			return;
		}

		UUID entityUUID = JSerializerUtility.deserializeUUID(uuidData);
		int componentCount = ByteUtils.readUint32LE(dataStream);

		Entity entity = uuidToEntity.get(entityUUID);

		for (int i = 0; i < componentCount; i++) {
			int componentTypeId = ByteUtils.readUint32LE(dataStream);
			ComponentType type = ComponentType.fromId(componentTypeId);
			String componentName = type.name();

			ComponentSerializer<Component, byte[]> componentSerializer = componentSerializerRegistry.getSerializer(componentName);

			if (componentSerializer == null) {
				Logger.warn("SceneSerializer deserializeEntity Failed, ComponentSerializer({}) Not Found, Skipping Component...", componentName);
				continue;
			}

			int componentSize = ByteUtils.readUint32LE(dataStream);
			byte[] componentData = new byte[componentSize];

			if (dataStream.read(componentData, 0, componentSize) != componentSize) {
				Logger.warn("SceneSerializer deserializeEntity Failed, Corrupted Component({}), Skipping Component...", componentName);
				continue;
			}

			Component component = componentSerializer.deserialize(componentData, resolver);
			entity.addComponent(component);
		}
	}
}
