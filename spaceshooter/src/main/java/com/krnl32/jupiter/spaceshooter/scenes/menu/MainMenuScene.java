package com.krnl32.jupiter.spaceshooter.scenes.menu;

import com.krnl32.jupiter.engine.asset.AssetID;
import com.krnl32.jupiter.engine.asset.AssetManager;
import com.krnl32.jupiter.engine.asset.types.FontAsset;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.components.gameplay.TransformComponent;
import com.krnl32.jupiter.engine.components.ui.*;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.ecs.Entity;
import com.krnl32.jupiter.engine.event.EventBus;
import com.krnl32.jupiter.engine.events.scene.SwitchSceneEvent;
import com.krnl32.jupiter.engine.events.window.WindowCloseEvent;
import com.krnl32.jupiter.engine.events.window.WindowResizeEvent;
import com.krnl32.jupiter.engine.renderer.Camera;
import com.krnl32.jupiter.engine.scene.Scene;
import com.krnl32.jupiter.engine.ui.UIHierarchyManager;
import com.krnl32.jupiter.engine.ui.layout.LayoutOverflow;
import com.krnl32.jupiter.engine.ui.layout.LayoutType;
import com.krnl32.jupiter.engine.ui.text.TextHorizontalAlign;
import com.krnl32.jupiter.engine.ui.text.TextOverflow;
import com.krnl32.jupiter.engine.ui.text.TextVerticalAlign;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MainMenuScene extends Scene {
	private int windowWidth, windowHeight;
	private AssetID arialFontID;
	public AssetID backgroundDarkPurpleID, backgroundBlackID, backgroundPurpleID, backgroundBlueID;
	private AssetID buttonBlueID, buttonGreenID, buttonRedID, buttonYellowID;

	public MainMenuScene(int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;

		EventBus.getInstance().register(WindowResizeEvent.class, event -> {
			this.windowWidth = event.getWidth();
			this.windowHeight = event.getHeight();
		});
	}

	@Override
	public void onCreate() {
		// Register Assets
		arialFontID = AssetManager.getInstance().registerAndLoad("fonts/arial.ttf", () -> new FontAsset("fonts/arial.ttf", 34));
		if (arialFontID == null)
			Logger.critical("Game Failed to Load Font Asset({})", "fonts/arial.ttf");

		backgroundDarkPurpleID = AssetManager.getInstance().registerAndLoad("textures/backgrounds/darkPurple.png", () -> new TextureAsset("textures/backgrounds/darkPurple.png"));
		if (backgroundDarkPurpleID == null)
			Logger.critical("MainMenuScene Failed to Load Texture Asset({})", "textures/backgrounds/darkPurple.png");

		backgroundBlackID = AssetManager.getInstance().registerAndLoad("textures/backgrounds/black.png", () -> new TextureAsset("textures/backgrounds/black.png"));
		if (backgroundBlackID == null)
			Logger.critical("MainMenuScene Failed to Load Texture Asset({})", "textures/backgrounds/black.png");

		backgroundPurpleID = AssetManager.getInstance().registerAndLoad("textures/backgrounds/purple.png", () -> new TextureAsset("textures/backgrounds/purple.png"));
		if (backgroundPurpleID == null)
			Logger.critical("MainMenuScene Failed to Load Texture Asset({})", "textures/backgrounds/purple.png");

		backgroundBlueID = AssetManager.getInstance().registerAndLoad("textures/backgrounds/blue.png", () -> new TextureAsset("textures/backgrounds/blue.png"));
		if (backgroundBlueID == null)
			Logger.critical("MainMenuScene Failed to Load Texture Asset({})", "textures/backgrounds/blue.png");

		buttonBlueID = AssetManager.getInstance().registerAndLoad("textures/ui/buttons/buttonBlue.png", () -> new TextureAsset("textures/ui/buttons/buttonBlue.png"));
		if (buttonBlueID == null)
			Logger.critical("MainMenuScene Failed to Load Texture Asset({})", "textures/ui/buttons/buttonBlue.png");

		buttonGreenID = AssetManager.getInstance().registerAndLoad("textures/ui/buttons/buttonGreen.png", () -> new TextureAsset("textures/ui/buttons/buttonGreen.png"));
		if (buttonGreenID == null)
			Logger.critical("MainMenuScene Failed to Load Texture Asset({})", "textures/ui/buttons/buttonGreen.png");

		buttonRedID = AssetManager.getInstance().registerAndLoad("textures/ui/buttons/buttonRed.png", () -> new TextureAsset("textures/ui/buttons/buttonRed.png"));
		if (buttonRedID == null)
			Logger.critical("MainMenuScene Failed to Load Texture Asset({})", "textures/ui/buttons/buttonRed.png");

		buttonYellowID = AssetManager.getInstance().registerAndLoad("textures/ui/buttons/buttonYellow.png", () -> new TextureAsset("textures/ui/buttons/buttonYellow.png"));
		if (buttonYellowID == null)
			Logger.critical("MainMenuScene Failed to Load Texture Asset({})", "textures/ui/buttons/buttonYellow.png");
	}

	@Override
	public void onActivate() {
		Entity cameraEntity = createEntity();
		cameraEntity.addComponent(new UUIDComponent());
		cameraEntity.addComponent(new TransformComponent(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		cameraEntity.addComponent(new CameraComponent(new Camera(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f, 0.0f, 50.0f, 1.0f, 45.0f, 10.0f, false), true));
		cameraEntity.getComponent(CameraComponent.class).camera.setOrthographic(10.0f, 0.1f, 100.0f);
		cameraEntity.getComponent(CameraComponent.class).camera.setViewport(windowWidth, windowHeight);

		Entity mainMenuRoot = createEntity();
		mainMenuRoot.addComponent(new UITransformComponent(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(windowWidth, windowHeight, 1.0f)));
		mainMenuRoot.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), backgroundPurpleID));

		Entity mainMenuButtonList = createEntity();
		mainMenuButtonList.addComponent(new UITransformComponent(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(windowWidth, windowHeight, 1.0f)));
		mainMenuButtonList.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), backgroundPurpleID));
		mainMenuButtonList.addComponent(new UILayoutComponent(LayoutType.VERTICAL, LayoutOverflow.SCALE, ((windowHeight + 400) / 2.0f), 0.0f, (windowWidth - 400) / 2.0f, (windowWidth - 400) / 2.0f, 0.0f, true));
		UIHierarchyManager.attach(mainMenuRoot, mainMenuButtonList);

		Entity gameTitleLabel = createEntity();
		gameTitleLabel.addComponent(new UITransformComponent(new Vector3f(((windowWidth - 300) / 2.0f), 0.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(500.0f, 500.0f, 1.0f)));
		gameTitleLabel.addComponent(new UITextComponent("Spaceshooter Game", new Vector4f(0.0f, 1.0f, 1.0f, 1.0f), arialFontID));
		UIHierarchyManager.attach(mainMenuRoot, gameTitleLabel);

		Entity playButton = createEntity();
		playButton.addComponent(new UITransformComponent(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(222.0f, 39.0f, 1.0f)));
		playButton.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), buttonBlueID));
		playButton.addComponent(new UIButtonComponent((entity) -> {
			EventBus.getInstance().emit(new SwitchSceneEvent("level1"));
		}));
		playButton.addComponent(new UITextComponent("Play", new Vector4f(0.705f, 0.118f, 0.118f, 1.0f), arialFontID, TextHorizontalAlign.CENTER, TextVerticalAlign.CENTER, TextOverflow.SCALE));
		UIHierarchyManager.attach(mainMenuButtonList, playButton);

		Entity quitButton = createEntity();
		quitButton.addComponent(new UITransformComponent(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(222.0f, 39.0f, 1.0f)));
		quitButton.addComponent(new UIRenderComponent(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), buttonRedID));
		quitButton.addComponent(new UIButtonComponent((entity) -> {
			EventBus.getInstance().emit(new WindowCloseEvent()); // Maybe EngineQuitEvent Instead?
		}));
		quitButton.addComponent(new UITextComponent("Quit", new Vector4f(0.705f, 0.118f, 0.118f, 1.0f), arialFontID, TextHorizontalAlign.CENTER, TextVerticalAlign.CENTER, TextOverflow.SCALE));
		UIHierarchyManager.attach(mainMenuButtonList, quitButton);
	}

	@Override
	public void onUnload() {
	}
}
