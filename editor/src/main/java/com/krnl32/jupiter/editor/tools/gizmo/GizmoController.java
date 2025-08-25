package com.krnl32.jupiter.editor.tools.gizmo;

import com.krnl32.jupiter.editor.editor.EditorCamera;
import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.input.InputActionSystem;
import com.krnl32.jupiter.engine.input.action.InputAction;
import com.krnl32.jupiter.engine.input.action.InputActionMap;
import com.krnl32.jupiter.engine.input.action.InputBinding;
import com.krnl32.jupiter.engine.input.devices.KeyCode;
import com.krnl32.jupiter.engine.renderer.ProjectionType;
import imgui.extension.imguizmo.ImGuizmo;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GizmoController {
	private final InputActionMap gizmoControls;
	private final EditorCamera editorCamera;

	private GizmoOperation operation;
	private GizmoMode mode;

	private float[] viewMatrix = new float[16];
	private float[] projectionMatrix = new float[16];
	private float[] transformMatrix = new float[16];

	private final Matrix4f newTransform = new Matrix4f();
	private final Vector3f translation = new Vector3f();
	private final Quaternionf rotationQuat = new Quaternionf();
	private final Vector3f rotationEuler = new Vector3f();
	private final Vector3f scale = new Vector3f();

	public GizmoController(EditorCamera editorCamera) {
		this.gizmoControls = new InputActionMap("EditorGizmoControls");
		this.editorCamera = editorCamera;
		this.operation = GizmoOperation.TRANSLATE;
		this.mode = GizmoMode.LOCAL;

		setupInputAction();
	}

	public void onUpdate(float dt) {
		if (gizmoControls.getAction("Translate").isActive()) {
			operation = GizmoOperation.TRANSLATE;
		} else if (gizmoControls.getAction("Rotate").isActive()) {
			operation = GizmoOperation.ROTATE;
		} else if (gizmoControls.getAction("Scale").isActive()) {
			operation = GizmoOperation.SCALE;
		} else if (gizmoControls.getAction("Bounds").isActive()) {
			operation = GizmoOperation.BOUNDS;
		} else if (gizmoControls.getAction("Transform").isActive()) {
			operation = GizmoOperation.UNIVERSAL;
		}
	}

	public void onRender(float dt, Entity entity, Vector2f viewportPosition, Vector2f viewportSize) {
		// Entity Transform
		TransformComponent transform = entity.getComponent(TransformComponent.class);

		if (transform == null) {
			return;
		}

		// ImGuizmo Matrices
		editorCamera.getCamera().getViewMatrix().get(viewMatrix);
		editorCamera.getCamera().getProjectionMatrix().get(projectionMatrix);
		transform.getTransform().get(transformMatrix);

		// Setup ImGuizmo
		boolean orthographic = editorCamera.getCamera().getProjectionType() == ProjectionType.ORTHOGRAPHIC;
		ImGuizmo.setOrthographic(orthographic);
		ImGuizmo.setDrawList();
		ImGuizmo.setRect(viewportPosition.x, viewportPosition.y, viewportSize.x, viewportSize.y);

		ImGuizmo.manipulate(viewMatrix, projectionMatrix, operation.getCode(), mode.getCode(), transformMatrix);

		// Get Updated Transform
		if(ImGuizmo.isUsing()) {
			newTransform.set(transformMatrix);
			newTransform.getTranslation(translation);
			newTransform.getUnnormalizedRotation(rotationQuat);
			newTransform.getScale(scale);

			// Convert Quat to Euler angles
			rotationQuat.getEulerAnglesXYZ(rotationEuler);

			// Update Entity Transform
			transform.translation.set(translation);
			transform.rotation.set(rotationEuler);
			transform.scale.set(scale);
		}
	}

	private void setupInputAction() {
		InputAction translate = new InputAction("Translate");
		translate.addBinding(new InputBinding(KeyCode.W));

		InputAction rotate = new InputAction("Rotate");
		rotate.addBinding(new InputBinding(KeyCode.E));

		InputAction scale = new InputAction("Scale");
		scale.addBinding(new InputBinding(KeyCode.R));

		InputAction bounds = new InputAction("Bounds");
		bounds.addBinding(new InputBinding(KeyCode.T));

		InputAction transform = new InputAction("Transform");
		transform.addBinding(new InputBinding(KeyCode.Y));

		gizmoControls.addAction(translate);
		gizmoControls.addAction(rotate);
		gizmoControls.addAction(scale);
		gizmoControls.addAction(bounds);
		gizmoControls.addAction(transform);

		InputActionSystem.getInstance().addInputActionMap(gizmoControls);
	}
}
