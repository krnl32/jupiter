package com.krnl32.jupiter.scenes.menu;

import com.krnl32.jupiter.asset.AssetID;
import com.krnl32.jupiter.asset.AssetManager;
import com.krnl32.jupiter.asset.TextureAsset;
import com.krnl32.jupiter.components.CameraComponent;
import com.krnl32.jupiter.components.TransformComponent;
import com.krnl32.jupiter.components.UUIDComponent;
import com.krnl32.jupiter.components.ui.*;
import com.krnl32.jupiter.core.Logger;
import com.krnl32.jupiter.ecs.Entity;
import com.krnl32.jupiter.scene.Scene;
import com.krnl32.jupiter.renderer.Camera;
import com.krnl32.jupiter.systems.*;
import com.krnl32.jupiter.systems.ui.*;
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
		addSystem(new UILayoutSystem(getRegistry()));
		addSystem(new UIInputSystem(getRegistry()));
		addSystem(new UIRenderSystem(getRegistry()));
		addSystem(new UIButtonSystem(getRegistry()));
		addSystem(new UITextRenderSystem(getRegistry()));
		addSystem(new UIScrollSystem(getRegistry()));
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
		button.addComponent(new UIButtonComponent((entity) -> {
			System.out.println("Clicked: " + entity.getTagOrId());
		}));

		Entity box = createEntity();
		box.addComponent(new UITransformComponent(new Vector3f(100.0f, 100.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(100.0f, 100.0f, 1.0f)));
		box.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 0.0f, 1.0f, 1.0f), null));
		box.addComponent(new UIInputStateComponent());
		box.addComponent(new UIInputEventComponent(
			(entity -> {
				System.out.println("Clicked: " + entity.getTagOrId());
			}),
			(entity -> {
				System.out.println("Hovering Start: " + entity.getTagOrId());
			}),
			(entity -> {
				System.out.println("Hovering End: " + entity.getTagOrId());
			}),
			(entity -> {
				System.out.println("Focusing: " + entity.getTagOrId());
			}),
			(entity -> {
				System.out.println("Blur: " + entity.getTagOrId());
			})));

		box = createEntity();
		box.addComponent(new UITransformComponent(new Vector3f(500.0f, 100.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(100.0f, 100.0f, 1.0f)));
		box.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 0.0f, 1.0f, 1.0f), null));
		box.addComponent(new UIInputStateComponent());
		box.addComponent(new UIInputEventComponent(
			(entity -> {
				System.out.println("Clicked: " + entity.getTagOrId());
			}),
			(entity -> {
				System.out.println("Hovering Start: " + entity.getTagOrId());
			}),
			(entity -> {
				System.out.println("Hovering End: " + entity.getTagOrId());
			}),
			(entity -> {
				System.out.println("Focusing: " + entity.getTagOrId());
			}),
			(entity -> {
				System.out.println("Blur: " + entity.getTagOrId());
			})));

		box = createEntity();
		box.addComponent(new UITransformComponent(new Vector3f(500.0f, 500.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(100.0f, 100.0f, 1.0f)));
		box.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 0.0f, 0.0f, 1.0f), null));
		box.addComponent(new UIFocusComponent((entity -> {
			System.out.println("Focusing: " + entity.getTagOrId());
		}), (entity -> {
			System.out.println("Blur: " + entity.getTagOrId());
		})));

		box = createEntity();
		box.addComponent(new UITransformComponent(new Vector3f(700.0f, 500.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(100.0f, 100.0f, 1.0f)));
		box.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 0.0f, 0.0f, 1.0f), null));
		box.addComponent(new UIFocusComponent((entity -> {
			System.out.println("Focusing: " + entity.getTagOrId());
		}), (entity -> {
			System.out.println("Blur: " + entity.getTagOrId());
		})));
	}

	@Override
	public void onUnload() {

	}
}
