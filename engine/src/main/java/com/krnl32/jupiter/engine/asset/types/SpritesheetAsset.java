package com.krnl32.jupiter.engine.asset.types;

import com.krnl32.jupiter.engine.asset.*;
import com.krnl32.jupiter.engine.core.Logger;
import com.krnl32.jupiter.engine.model.Sprite;
import com.krnl32.jupiter.engine.utility.FileIO;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpritesheetAsset extends Asset {
	private final String atlasMetadataPath;
	private String imagePath;
	private int atlasWidth, atlasHeight;
	private float scale;
	private Map<String, SpriteData> sprites;
	private AssetID textureAssetID;

	public SpritesheetAsset(String atlasMetadataPath) {
		super(AssetType.SPRITESHEET);
		this.atlasMetadataPath = atlasMetadataPath;
	}

	public Sprite getSprite(String name) {
		SpriteData spriteData = sprites.get(name);
		return (spriteData != null) ? new Sprite(-1, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), textureAssetID, spriteData.getUV()) : null;
	}

	@Override
	protected boolean load() {
		sprites = new HashMap<>();

		try {
			JSONObject root = new JSONObject(FileIO.readFileContent(getRootPath() + atlasMetadataPath));
			JSONObject spritesheet = root.getJSONObject("spritesheet");
			imagePath = spritesheet.getString("imagePath");
			atlasWidth = spritesheet.getJSONObject("size").getInt("width");
			atlasHeight = spritesheet.getJSONObject("size").getInt("height");
			scale = spritesheet.optFloat("scale", 1.0f);

			// Load Texture
			textureAssetID = AssetManager.getInstance().registerAndLoad(imagePath, () -> new TextureAsset(imagePath));
			if (textureAssetID == null) {
				Logger.error("SpritesheetAsset Failed to Load Texture Asset({})", imagePath);
				setState(AssetState.MISSING);
				return false;
			}

			// Sprites
			JSONArray spritesArrayData = spritesheet.getJSONArray("sprites");
			for (int i = 0; i < spritesArrayData.length(); i++) {
				JSONObject spriteObject = spritesArrayData.getJSONObject(i);
				String spriteName = spriteObject.getString("name");

				JSONObject spriteFrame = spriteObject.getJSONObject("frame");
				int spriteFrameX = spriteFrame.getInt("x");
				int spriteFrameY = spriteFrame.getInt("y");
				int spriteFrameWidth = spriteFrame.getInt("width");
				int spriteFrameHeight = spriteFrame.getInt("height");

				JSONObject spriteSourceSize = spriteObject.getJSONObject("sourceSize");
				int spriteSourceSizeWidth = spriteSourceSize.getInt("width");
				int spriteSourceSizeHeight = spriteSourceSize.getInt("height");

				JSONObject spriteOffset = spriteObject.getJSONObject("offset");
				Vector2f spriteOffsetVec = new Vector2f(spriteOffset.getInt("x"), spriteOffset.getInt("y"));

				boolean spriteRotated = spriteObject.getBoolean("rotated");
				boolean spriteTrimmed = spriteObject.getBoolean("trimmed");

				JSONObject spriteAnchor = spriteObject.getJSONObject("anchor");
				Vector2f spriteAnchorVec = (spriteAnchor != null)
					? new Vector2f(spriteAnchor.optFloat("x", 0.5f), spriteAnchor.optFloat("y", 0.5f))
					: new Vector2f(0.5f, 0.5f);

				SpriteData spriteData = new SpriteData(
					spriteName,
					new SpriteFrame(spriteFrameX, spriteFrameY, spriteFrameWidth, spriteFrameHeight),
					spriteSourceSizeWidth, spriteSourceSizeHeight,
					spriteOffsetVec,
					spriteRotated, spriteTrimmed,
					spriteAnchorVec,
					atlasWidth, atlasHeight
				);

				sprites.put(spriteName, spriteData);
			}

		} catch (JSONException e) {
			setState(AssetState.MISSING);
			Logger.error("Spritesheet({}) Failed to Parse: ", atlasMetadataPath, e.getMessage());
			return false;
		} catch (IOException e) {
			setState(AssetState.MISSING);
			Logger.error("Spritesheet({}) File Not Found: ", atlasMetadataPath, e.getMessage());
			return false;
		}

		setState(AssetState.LOADED);
		return true;
	}

	@Override
	protected boolean reload() {
		unload();
		return load();
	}

	@Override
	protected void unload() {
		sprites = null;
		if (textureAssetID != null) {
			AssetManager.getInstance().unregister(textureAssetID);
			textureAssetID = null;
		}
		setState(AssetState.UNLOADED);
	}
}

class SpriteData {
	private final String name;
	private final SpriteFrame spriteFrame;
	private final int sourceWidth, sourceHeight;
	private final Vector2f offset;
	private final boolean rotated;
	private final boolean trimmed;
	private final Vector2f anchor;
	private final float u1, v1, u2, v2;

	public SpriteData(String name, SpriteFrame spriteFrame, int sourceWidth, int sourceHeight, Vector2f offset, boolean rotated, boolean trimmed, Vector2f anchor, int atlasWidth, int atlasHeight) {
		this.name = name;
		this.spriteFrame = spriteFrame;
		this.sourceWidth = sourceWidth;
		this.sourceHeight = sourceHeight;
		this.offset = offset;
		this.rotated = rotated;
		this.trimmed = trimmed;
		this.anchor = anchor;

		if (rotated) {
			u1 = (float) spriteFrame.getX() / atlasWidth;
			v1 = (float) spriteFrame.getY() / atlasHeight;
			u2 = (float) (spriteFrame.getX() + spriteFrame.getHeight()) / atlasWidth;
			v2 = (float) (spriteFrame.getY() + spriteFrame.getWidth()) / atlasHeight;
		} else {
			u1 = (float) spriteFrame.getX() / atlasWidth;
			v1 = 1.0f - ((float) (spriteFrame.getY() + spriteFrame.getHeight()) / atlasHeight);
			u2 = (float) (spriteFrame.getX() + spriteFrame.getWidth()) / atlasWidth;
			v2 = 1.0f - ((float) (spriteFrame.getY()) / atlasHeight);
		}
	}

	public String getName() {
		return name;
	}

	public SpriteFrame getSpriteFrame() {
		return spriteFrame;
	}

	public int getSourceWidth() {
		return sourceWidth;
	}

	public int getSourceHeight() {
		return sourceHeight;
	}

	public Vector2f getOffset() {
		return offset;
	}

	public boolean isRotated() {
		return rotated;
	}

	public boolean isTrimmed() {
		return trimmed;
	}

	public Vector2f getAnchor() {
		return anchor;
	}

	public float[] getUV() {
		if (rotated)
			return getUVRotated();
		return new float[]{
			u1, v1, // BL
			u2, v1, // BR
			u2, v2, // TR
			u1, v2  // TL
		};
	}

	public float[] getUVRotated() {
		if (!rotated)
			return getUV();
		return new float[]{
			u1, v1, // BL
			u1, v2, // BR
			u2, v2, // TR
			u2, v1  // TL
		};
	}

	@Override
	public String toString() {
		return "SpriteData{" +
			"name='" + name + '\'' +
			", spriteFrame=" + spriteFrame +
			", sourceWidth=" + sourceWidth +
			", sourceHeight=" + sourceHeight +
			", offset=" + offset +
			", rotated=" + rotated +
			", trimmed=" + trimmed +
			", anchor=" + anchor +
			", UV=" + (rotated ? Arrays.toString(getUVRotated()) : Arrays.toString(getUV())) +
			'}';
	}
}

class SpriteFrame {
	private final int x, y;
	private final int width, height;

	public SpriteFrame(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "SpriteFrame{" +
			"x=" + x +
			", y=" + y +
			", width=" + width +
			", height=" + height +
			'}';
	}
}
