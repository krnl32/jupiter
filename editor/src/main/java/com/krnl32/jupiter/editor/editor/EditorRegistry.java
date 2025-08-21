package com.krnl32.jupiter.editor.editor;

import com.krnl32.jupiter.editor.asset.EditorAssetManager;
import com.krnl32.jupiter.editor.asset.importers.JSONSceneAssetImporter;
import com.krnl32.jupiter.editor.asset.importers.LuaScriptAssetImporter;
import com.krnl32.jupiter.editor.asset.importers.RasterTextureAssetImporter;
import com.krnl32.jupiter.editor.asset.loaders.EditorLuaScriptAssetLoader;
import com.krnl32.jupiter.editor.asset.loaders.EditorRasterTextureAssetLoader;
import com.krnl32.jupiter.editor.asset.loaders.EditorSceneAssetLoader;
import com.krnl32.jupiter.editor.factory.FactoryRegistry;
import com.krnl32.jupiter.editor.factory.components.effects.BlinkComponentFactory;
import com.krnl32.jupiter.editor.factory.components.effects.DeathEffectComponentFactory;
import com.krnl32.jupiter.editor.factory.components.effects.ParticleComponentFactory;
import com.krnl32.jupiter.editor.factory.components.gameplay.*;
import com.krnl32.jupiter.editor.factory.components.input.KeyboardControlComponentFactory;
import com.krnl32.jupiter.editor.factory.components.physics.BoxCollider2DComponentFactory;
import com.krnl32.jupiter.editor.factory.components.physics.CircleCollider2DComponentFactory;
import com.krnl32.jupiter.editor.factory.components.physics.Rigidbody2DComponentFactory;
import com.krnl32.jupiter.editor.factory.components.projectile.ProjectileComponentFactory;
import com.krnl32.jupiter.editor.factory.components.projectile.ProjectileEmitterComponentFactory;
import com.krnl32.jupiter.editor.factory.components.renderer.CameraComponentFactory;
import com.krnl32.jupiter.editor.factory.components.renderer.SpriteRendererComponentFactory;
import com.krnl32.jupiter.editor.factory.components.utility.LifetimeComponentFactory;
import com.krnl32.jupiter.editor.factory.components.utility.TagComponentFactory;
import com.krnl32.jupiter.editor.factory.components.utility.UUIDComponentFactory;
import com.krnl32.jupiter.editor.renderer.EditorRendererRegistry;
import com.krnl32.jupiter.editor.renderer.asset.assets.SceneAssetRenderer;
import com.krnl32.jupiter.editor.renderer.asset.assets.ScriptAssetRenderer;
import com.krnl32.jupiter.editor.renderer.asset.assets.TextureAssetRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.effects.BlinkComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.effects.ParticleComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.gameplay.*;
import com.krnl32.jupiter.editor.renderer.component.components.physics.BoxCollider2DComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.physics.CircleCollider2DComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.physics.RigidBody2DComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.renderer.SpriteRendererComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.utility.LifetimeComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.utility.TagComponentRenderer;
import com.krnl32.jupiter.editor.renderer.component.components.utility.UUIDComponentRenderer;
import com.krnl32.jupiter.engine.asset.handle.AssetType;
import com.krnl32.jupiter.engine.asset.loader.AssetLoaderRegistry;
import com.krnl32.jupiter.engine.asset.types.SceneAsset;
import com.krnl32.jupiter.engine.asset.types.ScriptAsset;
import com.krnl32.jupiter.engine.asset.types.TextureAsset;
import com.krnl32.jupiter.engine.cloner.utility.DefaultComponentCloners;
import com.krnl32.jupiter.engine.components.effects.BlinkComponent;
import com.krnl32.jupiter.engine.components.effects.DeathEffectComponent;
import com.krnl32.jupiter.engine.components.effects.ParticleComponent;
import com.krnl32.jupiter.engine.components.gameplay.*;
import com.krnl32.jupiter.engine.components.input.KeyboardControlComponent;
import com.krnl32.jupiter.engine.components.physics.BoxCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.CircleCollider2DComponent;
import com.krnl32.jupiter.engine.components.physics.RigidBody2DComponent;
import com.krnl32.jupiter.engine.components.projectile.ProjectileComponent;
import com.krnl32.jupiter.engine.components.projectile.ProjectileEmitterComponent;
import com.krnl32.jupiter.engine.components.renderer.CameraComponent;
import com.krnl32.jupiter.engine.components.renderer.SpriteRendererComponent;
import com.krnl32.jupiter.engine.components.utility.LifetimeComponent;
import com.krnl32.jupiter.engine.components.utility.TagComponent;
import com.krnl32.jupiter.engine.components.utility.UUIDComponent;
import com.krnl32.jupiter.engine.sceneserializer.SceneSerializer;
import com.krnl32.jupiter.engine.script.lua.utility.LuaComponentBinders;

import java.util.Map;

public class EditorRegistry {
	// Engine
	public static void registerAssetImporters(EditorAssetManager editorAssetManager, SceneSerializer<Map<String, Object>> sceneSerializer) {
		editorAssetManager.registerAssetImporter(new RasterTextureAssetImporter());
		editorAssetManager.registerAssetImporter(new JSONSceneAssetImporter(sceneSerializer));
		editorAssetManager.registerAssetImporter(new LuaScriptAssetImporter());
	}

	public static void registerAssetLoaders(SceneSerializer<Map<String, Object>> sceneSerializer) {
		AssetLoaderRegistry.register(AssetType.TEXTURE, TextureAsset.class, new EditorRasterTextureAssetLoader());
		AssetLoaderRegistry.register(AssetType.SCENE, SceneAsset.class, new EditorSceneAssetLoader(sceneSerializer));
		AssetLoaderRegistry.register(AssetType.SCRIPT, ScriptAsset.class, new EditorLuaScriptAssetLoader());
	}

	public static void registerComponentCloners() {
		DefaultComponentCloners.registerAll();
	}

	public static void registerComponentBinders() {
		LuaComponentBinders.registerAll();
	}

	// Editor
	public static void registerComponentFactories() {
		// Effects
		FactoryRegistry.registerComponentFactory(BlinkComponent.class, new BlinkComponentFactory());
		FactoryRegistry.registerComponentFactory(DeathEffectComponent.class, new DeathEffectComponentFactory());
		FactoryRegistry.registerComponentFactory(ParticleComponent.class, new ParticleComponentFactory());

		// Gameplay
		FactoryRegistry.registerComponentFactory(TransformComponent.class, new TransformComponentFactory());
		FactoryRegistry.registerComponentFactory(TeamComponent.class, new TeamComponentFactory());
		FactoryRegistry.registerComponentFactory(HealthComponent.class, new HealthComponentFactory());
		FactoryRegistry.registerComponentFactory(MovementIntentComponent.class, new MovementIntentComponentFactory());
		FactoryRegistry.registerComponentFactory(ForceMovementComponent.class, new ForceMovementComponentFactory());
		FactoryRegistry.registerComponentFactory(ScriptComponent.class, new ScriptComponentFactory());

		// Input
		FactoryRegistry.registerComponentFactory(KeyboardControlComponent.class, new KeyboardControlComponentFactory());

		// Physics
		FactoryRegistry.registerComponentFactory(RigidBody2DComponent.class, new Rigidbody2DComponentFactory());
		FactoryRegistry.registerComponentFactory(BoxCollider2DComponent.class, new BoxCollider2DComponentFactory());
		FactoryRegistry.registerComponentFactory(CircleCollider2DComponent.class, new CircleCollider2DComponentFactory());

		// Projectile
		FactoryRegistry.registerComponentFactory(ProjectileComponent.class, new ProjectileComponentFactory());
		FactoryRegistry.registerComponentFactory(ProjectileEmitterComponent.class, new ProjectileEmitterComponentFactory());

		// Renderer
		FactoryRegistry.registerComponentFactory(CameraComponent.class, new CameraComponentFactory());
		FactoryRegistry.registerComponentFactory(SpriteRendererComponent.class, new SpriteRendererComponentFactory());

		// Utility
		FactoryRegistry.registerComponentFactory(UUIDComponent.class, new UUIDComponentFactory());
		FactoryRegistry.registerComponentFactory(TagComponent.class, new TagComponentFactory());
		FactoryRegistry.registerComponentFactory(LifetimeComponent.class, new LifetimeComponentFactory());
	}

	public static void registerComponentRenderers() {
		// Effects
		EditorRendererRegistry.registerComponentRenderer(BlinkComponent.class, new BlinkComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(ParticleComponent.class, new ParticleComponentRenderer());

		// Gameplay
		EditorRendererRegistry.registerComponentRenderer(TransformComponent.class, new TransformComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(TeamComponent.class, new TeamComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(HealthComponent.class, new HealthComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(MovementIntentComponent.class, new MovementIntentComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(ForceMovementComponent.class, new ForceMovementComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(ScriptComponent.class, new ScriptComponentRenderer());

		// Input

		// Physics
		EditorRendererRegistry.registerComponentRenderer(RigidBody2DComponent.class, new RigidBody2DComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(BoxCollider2DComponent.class, new BoxCollider2DComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(CircleCollider2DComponent.class, new CircleCollider2DComponentRenderer());

		// Projectile

		// Renderer
		EditorRendererRegistry.registerComponentRenderer(SpriteRendererComponent.class, new SpriteRendererComponentRenderer());

		// Utility
		EditorRendererRegistry.registerComponentRenderer(UUIDComponent.class, new UUIDComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(TagComponent.class, new TagComponentRenderer());
		EditorRendererRegistry.registerComponentRenderer(LifetimeComponent.class, new LifetimeComponentRenderer());
	}

	public static void registerAssetRenderers(SceneSerializer<Map<String, Object>> sceneSerializer) {
		EditorRendererRegistry.registerAssetRenderer(AssetType.TEXTURE, new TextureAssetRenderer());
		EditorRendererRegistry.registerAssetRenderer(AssetType.SCENE, new SceneAssetRenderer(sceneSerializer));
		EditorRendererRegistry.registerAssetRenderer(AssetType.SCRIPT, new ScriptAssetRenderer());
	}
}
