package com.krnl32.jupiter.engine.sceneserializer.data.components.renderer;

import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.renderer.Camera;
import com.krnl32.jupiter.engine.renderer.ProjectionType;
import com.krnl32.jupiter.engine.sceneserializer.ComponentSerializer;
import com.krnl32.jupiter.engine.sceneserializer.resolvers.EntityResolver;
import com.krnl32.jupiter.engine.sceneserializer.data.utility.DataSerializerUtility;

import java.util.Map;

public class DataCameraComponentSerializer implements ComponentSerializer<CameraComponent, Map<String, Object>> {
	@Override
	public Map<String, Object> serialize(CameraComponent component) {
		return Map.ofEntries(
			Map.entry("position", DataSerializerUtility.serializeVector3f(component.camera.getPosition())),
			Map.entry("worldUp", DataSerializerUtility.serializeVector3f(component.camera.getWorldUp())),
			Map.entry("yaw", component.camera.getYaw()),
			Map.entry("pitch", component.camera.getPitch()),
			Map.entry("roll", component.camera.getRoll()),
			Map.entry("zoom", component.camera.getZoom()),
			Map.entry("turnSpeed", component.camera.getTurnSpeed()),
			Map.entry("rollSpeed", component.camera.getRollSpeed()),
			Map.entry("zoomSpeed", component.camera.getZoomSpeed()),
			Map.entry("mouseEnabled", component.camera.isMouseEnabled()),
			Map.entry("projectionType", component.camera.getProjectionType().name()),
			Map.entry("projectionFOV", component.camera.getProjectionFOV()),
			Map.entry("projectionNear", component.camera.getProjectionNear()),
			Map.entry("projectionFar", component.camera.getProjectionFar()),
			Map.entry("projectionSize", component.camera.getProjectionSize()),
			Map.entry("aspectRatio", component.camera.getAspectRatio()),
			Map.entry("primary", component.primary)
		);
	}

	@Override
	public CameraComponent deserialize(Map<String, Object> data, EntityResolver resolver) {
		Camera camera = new Camera(
			DataSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("position")),
			DataSerializerUtility.deserializeVector3f((Map<String, Object>) data.get("worldUp")),
			DataSerializerUtility.toFloat(data.get("yaw")),
			DataSerializerUtility.toFloat(data.get("pitch")),
			DataSerializerUtility.toFloat(data.get("roll")),
			DataSerializerUtility.toFloat(data.get("zoom")),
			DataSerializerUtility.toFloat(data.get("turnSpeed")),
			DataSerializerUtility.toFloat(data.get("rollSpeed")),
			DataSerializerUtility.toFloat(data.get("zoomSpeed")),
			(boolean) data.get("mouseEnabled")
		);

		float projectionNear = DataSerializerUtility.toFloat(data.get("projectionNear"));
		float projectionFar = DataSerializerUtility.toFloat(data.get("projectionFar"));

		ProjectionType projectionType = ProjectionType.valueOf(data.get("projectionType").toString());
		if (projectionType == ProjectionType.ORTHOGRAPHIC) {
			float projectionSize = DataSerializerUtility.toFloat(data.get("projectionSize"));
			camera.setOrthographic(projectionSize, projectionNear, projectionFar);
		}
		else if(projectionType == ProjectionType.PERSPECTIVE) {
			float projectionFOV = DataSerializerUtility.toFloat(data.get("projectionFOV"));
			camera.setPerspective(projectionFOV, projectionNear, projectionFar);
		}

		camera.setAspectRatio(DataSerializerUtility.toFloat(data.get("aspectRatio")));

		return new CameraComponent(camera, (boolean) data.get("primary"));
	}
}
