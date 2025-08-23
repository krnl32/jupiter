package com.krnl32.jupiter.engine.sceneserializer.jnative.components.renderer;

import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.renderer.Camera;
import com.krnl32.jupiter.engine.renderer.ProjectionType;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.jnative.utility.JSerializerUtility;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import org.joml.Vector3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * == CameraComponent (size: 75 Bytes) ==
 * 12 Bytes	Position		float32 x 3
 * 12 Bytes	WorldUp			float32 x 3
 * 4 Bytes	Yaw				float32
 * 4 Bytes	Pitch			float32
 * 4 Bytes	Roll			float32
 * 4 Bytes	Zoom			float32
 * 4 Bytes	TurnSpeed		float32
 * 4 Bytes	RollSpeed		float32
 * 4 Bytes	ZoomSpeed		float32
 * 1 Byte	MouseEnabled	uint8
 * 1 Byte	ProjectionType	uint8
 * 4 Bytes	ProjectionFOV	float32
 * 4 Bytes	ProjectionNear	float32
 * 4 Bytes	ProjectionFar	float32
 * 4 Bytes	ProjectionSize	float32
 * 4 Bytes	AspectRatio		float32
 * 1 Byte	Primary			uint8
 */
public class JCameraComponentSerializer implements ComponentSerializer<CameraComponent, byte[]> {
	private static final int SIZE = 75;
	private static final ByteOrder ENDIANNESS = ByteOrder.LITTLE_ENDIAN;

	@Override
	public byte[] serialize(CameraComponent component) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ENDIANNESS);
		JSerializerUtility.serializeVector3f(buffer, component.camera.getPosition());
		JSerializerUtility.serializeVector3f(buffer, component.camera.getWorldUp());
		JSerializerUtility.serializeFloat(buffer, component.camera.getYaw());
		JSerializerUtility.serializeFloat(buffer, component.camera.getPitch());
		JSerializerUtility.serializeFloat(buffer, component.camera.getRoll());
		JSerializerUtility.serializeFloat(buffer, component.camera.getZoom());
		JSerializerUtility.serializeFloat(buffer, component.camera.getTurnSpeed());
		JSerializerUtility.serializeFloat(buffer, component.camera.getRollSpeed());
		JSerializerUtility.serializeFloat(buffer, component.camera.getZoomSpeed());
		JSerializerUtility.serializeBool(buffer, component.camera.isMouseEnabled());
		JSerializerUtility.serializeByte(buffer, component.camera.getProjectionType().getId());
		JSerializerUtility.serializeFloat(buffer, component.camera.getProjectionFOV());
		JSerializerUtility.serializeFloat(buffer, component.camera.getProjectionNear());
		JSerializerUtility.serializeFloat(buffer, component.camera.getProjectionFar());
		JSerializerUtility.serializeFloat(buffer, component.camera.getProjectionSize());
		JSerializerUtility.serializeFloat(buffer, component.camera.getAspectRatio());
		JSerializerUtility.serializeBool(buffer, component.primary);

		return buffer.array();
	}

	@Override
	public CameraComponent deserialize(byte[] data, EntityResolver resolver) {
		if (data.length != SIZE) {
			Logger.error("JCameraComponentSerializer Deserialize failed, Corrupted Data, Size({})", data.length);
			return null;
		}

		ByteBuffer buffer = ByteBuffer.wrap(data).order(ENDIANNESS);
		Vector3f position = JSerializerUtility.deserializeVector3f(buffer);
		Vector3f worldUp = JSerializerUtility.deserializeVector3f(buffer);
		float yaw = JSerializerUtility.deserializeFloat(buffer);
		float pitch = JSerializerUtility.deserializeFloat(buffer);
		float roll = JSerializerUtility.deserializeFloat(buffer);
		float zoom = JSerializerUtility.deserializeFloat(buffer);
		float turnSpeed = JSerializerUtility.deserializeFloat(buffer);
		float rollSpeed = JSerializerUtility.deserializeFloat(buffer);
		float zoomSpeed = JSerializerUtility.deserializeFloat(buffer);
		boolean mouseEnabled = JSerializerUtility.deserializeBool(buffer);
		byte projectionTypeId = JSerializerUtility.deserializeByte(buffer);
		float projectionFOV = JSerializerUtility.deserializeFloat(buffer);
		float projectionNear = JSerializerUtility.deserializeFloat(buffer);
		float projectionFar = JSerializerUtility.deserializeFloat(buffer);
		float projectionSize = JSerializerUtility.deserializeFloat(buffer);
		float aspectRatio = JSerializerUtility.deserializeFloat(buffer);
		boolean primary = JSerializerUtility.deserializeBool(buffer);

		Camera camera = new Camera(
			position, worldUp,
			yaw, pitch, roll, zoom,
			turnSpeed, rollSpeed, zoomSpeed,
			mouseEnabled
		);

		ProjectionType projectionType = ProjectionType.fromId(projectionTypeId);

		if (projectionType == ProjectionType.ORTHOGRAPHIC) {
			camera.setOrthographic(projectionSize, projectionNear, projectionFar);
		} else if(projectionType == ProjectionType.PERSPECTIVE) {
			camera.setPerspective(projectionFOV, projectionNear, projectionFar);
		}

		camera.setAspectRatio(aspectRatio);

		return new CameraComponent(camera, primary);
	}
}
