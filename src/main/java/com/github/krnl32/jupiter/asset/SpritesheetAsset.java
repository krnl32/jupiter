package com.github.krnl32.jupiter.asset;

import com.github.krnl32.jupiter.core.Logger;
import com.github.krnl32.jupiter.utility.FileIO;
import org.joml.Vector2f;
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
	private Map<String, Sprite> sprites;

	public SpritesheetAsset(String atlasMetadataPath) {
		super(AssetType.SPRITESHEET);
		this.atlasMetadataPath = atlasMetadataPath;
	}

	public float[] getSpriteUV(String name) {
		Sprite sprite = sprites.get(name);
		System.out.println(sprite);
		return (sprite != null) ? sprite.getUV() : null;
	}

	@Override
	protected boolean load() {
		sprites = new HashMap<>();

		try {
			JSONObject root = new JSONObject(FileIO.readFileContent(getRootPath() + atlasMetadataPath));
			JSONObject spritesheet = root.getJSONObject("spritesheet");
			imagePath = getRootPath() + spritesheet.getString("imagePath");
			atlasWidth = spritesheet.getJSONObject("size").getInt("width");
			atlasHeight = spritesheet.getJSONObject("size").getInt("height");
			scale = spritesheet.optFloat("scale", 1.0f);

			// Sprites
			JSONArray spritesData = spritesheet.getJSONArray("sprites");
			for (int i = 0; i < spritesData.length(); i++) {
				JSONObject spriteData = spritesData.getJSONObject(i);
				String spriteName = spriteData.getString("name");

				JSONObject spriteFrame = spriteData.getJSONObject("frame");
				int spriteFrameX = spriteFrame.getInt("x");
				int spriteFrameY = spriteFrame.getInt("y");
				int spriteFrameWidth = spriteFrame.getInt("width");
				int spriteFrameHeight = spriteFrame.getInt("height");

				JSONObject spriteSourceSize = spriteData.getJSONObject("sourceSize");
				int spriteSourceSizeWidth = spriteSourceSize.getInt("width");
				int spriteSourceSizeHeight = spriteSourceSize.getInt("height");

				JSONObject spriteOffset = spriteData.getJSONObject("offset");
				Vector2f spriteOffsetVec = new Vector2f(spriteOffset.getInt("x"), spriteOffset.getInt("y"));

				boolean spriteRotated = spriteData.getBoolean("rotated");
				boolean spriteTrimmed = spriteData.getBoolean("trimmed");

				JSONObject spriteAnchor = spriteData.getJSONObject("anchor");
				Vector2f spriteAnchorVec = (spriteAnchor != null)
					? new Vector2f(spriteAnchor.optFloat("x", 0.5f), spriteAnchor.optFloat("y", 0.5f))
					: new Vector2f(0.5f, 0.5f);

				Sprite sprite = new Sprite(
					spriteName,
					new Frame(spriteFrameX, spriteFrameY, spriteFrameWidth, spriteFrameHeight),
					spriteSourceSizeWidth, spriteSourceSizeHeight,
					spriteOffsetVec,
					spriteRotated, spriteTrimmed,
					spriteAnchorVec,
					atlasWidth, atlasHeight
				);

				sprites.put(spriteName, sprite);
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
		sprites = null;
		return load();
	}

	@Override
	protected void unload() {
		sprites = null;
		setState(AssetState.UNLOADED);
	}
}

class Sprite {
	private final String name;
	private final Frame frame;
	private final int sourceWidth, sourceHeight;
	private final Vector2f offset;
	private final boolean rotated;
	private final boolean trimmed;
	private final Vector2f anchor;
	private final float u1, v1, u2, v2;

	public Sprite(String name, Frame frame, int sourceWidth, int sourceHeight, Vector2f offset, boolean rotated, boolean trimmed, Vector2f anchor, int atlasWidth, int atlasHeight) {
		this.name = name;
		this.frame = frame;
		this.sourceWidth = sourceWidth;
		this.sourceHeight = sourceHeight;
		this.offset = offset;
		this.rotated = rotated;
		this.trimmed = trimmed;
		this.anchor = anchor;

		if (rotated) {
			u1 = (float) frame.getX() / atlasWidth;
			v1 = (float) frame.getY() / atlasHeight;
			u2 = (float) (frame.getX() + frame.getHeight()) / atlasWidth;
			v2 = (float) (frame.getY() + frame.getWidth()) / atlasHeight;
		} else {
			u1 = (float) frame.getX() / atlasWidth;
			v1 = (float) frame.getY() / atlasHeight;
			u2 = (float) (frame.getX() + frame.getWidth()) / atlasWidth;
			v2 = (float) (frame.getY() + frame.getHeight()) / atlasHeight;
		}
	}

	public String getName() {
		return name;
	}

	public Frame getFrame() {
		return frame;
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
			u1, v2, // BL
			u2, v2, // BR
			u2, v1, // TR
			u1, v1  // TL
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
		return "Sprite{" +
			"name='" + name + '\'' +
			", frame=" + frame +
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

class Frame {
	private final int x, y;
	private final int width, height;

	public Frame(int x, int y, int width, int height) {
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
		return "Frame{" +
			"x=" + x +
			", y=" + y +
			", width=" + width +
			", height=" + height +
			'}';
	}
}
