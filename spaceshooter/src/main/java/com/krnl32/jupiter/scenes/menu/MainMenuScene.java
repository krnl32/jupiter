package com.krnl32.jupiter.scenes.menu;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.TextureAsset;
import com.krnl32.jupiter.components.CameraComponent;
import com.krnl32.jupiter.components.TransformComponent;
import com.krnl32.jupiter.components.UUIDComponent;
import com.krnl32.jupiter.components.ui.UIButtonComponent;
import com.krnl32.jupiter.components.ui.UIRenderComponent;
import com.krnl32.jupiter.components.ui.UITransformComponent;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.game.Scene;
import com.krnl32.jupiter.renderer.Camera;
import com.krnl32.jupiter.systems.*;
import com.krnl32.jupiter.systems.ui.UIButtonSystem;
import com.krnl32.jupiter.systems.ui.UIRenderSystem;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MainMenuScene extends Scene {
	private AssetID buttonBlueID;

	@Override
	public void onCreate() {
		buttonBlueID = AssetManager.getInstance().registerAndLoad("textures/ui/button_blue.png", () -> new TextureAsset("textures/ui/button_blue.png"));
		if (buttonBlueID == null)
			Logger.critical("MainMenuScene Failed to Load Texture Asset({})", "textures/ui/button_blue.png");

		// Systems
		addSystem(new MovementSystem(getRegistry()), 0, true);
		addSystem(new KeyboardControlSystem(getRegistry()), 1, true);
		addSystem(new CameraSystem(getRegistry()), 2, true);
		addSystem(new RenderSystem(getRegistry()));
		addSystem(new LifetimeSystem(getRegistry()));
		addSystem(new ProjectileEmitterSystem(getRegistry()));
		addSystem(new CollisionSystem(getRegistry()));
		addSystem(new DamageSystem(getRegistry()));
		addSystem(new HealthSystem(getRegistry()));
		addSystem(new DestroySystem(getRegistry()));
		addSystem(new BlinkSystem(getRegistry()));
		addSystem(new ParticleSystem(getRegistry()));
		addSystem(new DeathEffectSystem(getRegistry()));
		addSystem(new UIRenderSystem(getRegistry()));
		addSystem(new UIButtonSystem(getRegistry()));
	}

	@Override
	public void onActivate() {
		Entity cameraEntity = createEntity();
		cameraEntity.addComponent(new UUIDComponent());
		cameraEntity.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraEntity.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false), true));
		cameraEntity.getComponent(CameraComponent.class).camera.setOrthographic(10.0f, 0.1f, 100.0f);
		cameraEntity.getComponent(CameraComponent.class).camera.setViewport(1920, 1080);

		Entity button = createEntity();
		button.addComponent(new UITransformComponent(new Vector3f(500.0f, 700.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(222.0f, 39.0f, 1.0f)));
		button.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), buttonBlueID));
		button.addComponent(new UIButtonComponent(() -> {
			System.out.println("Clicked");
		}));
	}

	@Override
	public void onUnload() {

	}
}
