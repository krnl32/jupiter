package com.krnl32.jupiter;

import com.krnl32.jupiter.components.*;
import com.krnl32.jupiter.core.Engine;
import com.krnl32.jupiter.editor.EditorUI;
import com.krnl32.jupiter.panels.SceneHierarchyPanel;
import com.krnl32.jupiter.renderer.FrameBufferAttachmentFormat;
import com.krnl32.jupiter.renderer.Framebuffer;
import com.krnl32.jupiter.renderer.Renderer;
import com.krnl32.jupiter.scene.SceneManager;
import com.krnl32.jupiter.serializer.SerializerRegistry;
import com.krnl32.jupiter.serializer.components.*;
import com.krnl32.jupiter.panels.ViewportPanel;
import org.joml.Vector4f;

import java.util.List;

public class Editor extends Engine {
	private Framebuffer framebuffer;
	private EditorUI editorUI;
	private SceneManager sceneManager;

	public Editor(String name, int width, int height) {
		super(name, width, height);
	}

	@Override
	public boolean onInit() {
		// Register Component Serializers
		SerializerRegistry.registerComponentSerializer(UUIDComponent.class, new UUIDComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TagComponent.class, new TagComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TransformComponent.class, new TransformComponentSerializer());
		SerializerRegistry.registerComponentSerializer(SpriteRendererComponent.class, new SpriteRendererComponentSerializer());
		SerializerRegistry.registerComponentSerializer(CameraComponent.class, new CameraComponentSerializer());
		SerializerRegistry.registerComponentSerializer(KeyboardControlComponent.class, new KeyboardControlComponentSerializer());
		SerializerRegistry.registerComponentSerializer(TeamComponent.class, new TeamComponentSerializer());
		SerializerRegistry.registerComponentSerializer(HealthComponent.class, new HealthComponentSerializer());
		SerializerRegistry.registerComponentSerializer(LifetimeComponent.class, new LifetimeComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ParticleComponent.class, new ParticleComponentSerializer());
		SerializerRegistry.registerComponentSerializer(BlinkComponent.class, new BlinkComponentSerializer());
		SerializerRegistry.registerComponentSerializer(DeathEffectComponent.class, new DeathEffectComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ProjectileComponent.class, new ProjectileComponentSerializer());
		SerializerRegistry.registerComponentSerializer(ProjectileEmitterComponent.class, new ProjectileEmitterComponentSerializer());

		sceneManager = new SceneManager();
		sceneManager.addScene("empty", new EmptyScene());
		sceneManager.switchScene("empty");

		framebuffer = new Framebuffer(getWindow().getWidth(), getWindow().getHeight(), List.of(FrameBufferAttachmentFormat.RGBA8, FrameBufferAttachmentFormat.RED_INTEGER));
		getRenderer().setFramebuffer(framebuffer);

		// Editor
		editorUI = new EditorUI(getWindow());
		editorUI.addEditorPanel(new ViewportPanel(framebuffer));
		editorUI.addEditorPanel(new SceneHierarchyPanel(sceneManager.getScene()));

		return true;
	}

	@Override
	public void onUpdate(float dt) {
		sceneManager.onUpdate(dt);
		editorUI.onUpdate(dt);
		editorUI.onRender(dt);
	}

	@Override
	public void onRender(float dt, Renderer renderer) {
		renderer.setClearColor(new Vector4f(0.07f, 0.13f, 0.17f, 1.0f));
		sceneManager.onRender(dt, renderer);
	}
}
