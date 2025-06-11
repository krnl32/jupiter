package com.github.krnl32.jupiter.game;

import com.github.krnl32.jupiter.asset.AssetID;
import com.github.krnl32.jupiter.asset.AssetManager;
import com.github.krnl32.jupiter.asset.TextureAsset;
import com.github.krnl32.jupiter.components.SpriteRendererComponent;
import com.github.krnl32.jupiter.components.TransformComponent;
import com.github.krnl32.jupiter.gameobjects.EmptyGameObject;
import com.github.krnl32.jupiter.renderer.Sprite;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Level {
	private final List<GameObject> gameObjects = new ArrayList<>();

	public Level(String filename) {
		try (FileReader reader = new FileReader(filename)) {
			JSONObject json = new JSONObject(new JSONTokener(reader));
			JSONArray jsonObjects = json.getJSONArray("gameObjects");

			for (int i = 0; i < jsonObjects.length(); i++) {
				JSONObject obj = jsonObjects.getJSONObject(i);

				// === Parse Transform ===
				Vector3f position = parseVec3(obj.getJSONArray("position"));
				Vector3f scale = parseVec3(obj.getJSONArray("scale"));
				Vector3f rotation = parseVec3(obj.optJSONArray("rotation"), new Vector3f(0, 0, 0));

				// === Optional Texture + Color ===
				String texturePath = obj.optString("texture", null);
				AssetID textureID = null;
				if (texturePath != null && !texturePath.isEmpty()) {
					textureID = AssetManager.getInstance().registerAndLoad(texturePath, () -> new TextureAsset(texturePath));
				}

				Vector4f color = parseVec4(obj.optJSONArray("color"), new Vector4f(1, 1, 1, 1));

				// === Build GameObject ===
				GameObject go = new EmptyGameObject();
				go.addComponent(new TransformComponent(position, rotation, scale));
				go.addComponent(new SpriteRendererComponent(new Sprite(
					1, 1, 0, color, textureID
				)));

				gameObjects.add(go);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	private Vector3f parseVec3(JSONArray arr) {
		return new Vector3f(arr.getFloat(0), arr.getFloat(1), arr.getFloat(2));
	}

	private Vector3f parseVec3(JSONArray arr, Vector3f fallback) {
		if (arr == null) return fallback;
		return parseVec3(arr);
	}

	private Vector4f parseVec4(JSONArray arr, Vector4f fallback) {
		if (arr == null) return fallback;
		return new Vector4f(arr.getFloat(0), arr.getFloat(1), arr.getFloat(2), arr.getFloat(3));
	}
}
